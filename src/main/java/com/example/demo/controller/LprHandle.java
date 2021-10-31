package com.example.demo.controller;

import com.example.demo.entity.ParkRecord;
import com.example.demo.entity.Parking;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.ParkRecordRepository;
import com.example.demo.repository.ParkingRepository;
import com.example.demo.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author: Elodie
 * @Date: 2021/7/30 14:58
 */
@Api(value = "车牌识别")
@Controller
@RequestMapping("/recognition")
public class LprHandle {
    @ApiOperation(value = "车牌识别",notes = "调用本地python识别")
    @PostMapping("/lpr")
    public int main(String[] args){
        try{
            System.out.println("start");
            Process pr = Runtime.getRuntime().exec("cmd /k start D:\\Anaconda\\anaconda3\\envs\\hyperLPR\\python.exe D:\\hyperlpr\\HyperLPR-Meow-master\\lpr_video.py C:\\Users\\98379\\Desktop\\202108021555.mp4");
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(pr.getInputStream()));
            String line;
            System.out.println(pr.getInputStream());
            while ((line = in.readLine()) != null) {
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            return 2;
        }

    }
//    @GetMapping("/test/{license}/{date}")
//    @ResponseBody
//    public String[] test(@PathVariable("license") String[] license,@PathVariable("date") String[] date){
//        System.out.println(license.length);
//        for(int i=0;i<license.length;i++) {
//            System.out.println(license[i]);
//            System.out.println(date[i]);
//        }
//
//        return license;
//    }
//    @PostMapping("/test")
//    @ResponseBody
//    public String[] test(@RequestParam("license") String[] license){
//        System.out.println(license.length);
//        for(int i=0;i<license.length;i++) {
//            System.out.println(license[i]);
//        }
//        return license;
//    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private ParkRecordRepository parkRecordRepository;
    /**
     * 上传车辆入场excel数据
     * @param license
     * @param date
     */
    @ApiOperation("上传车辆入场excel数据")
    @GetMapping("/uploadEntranLpr/{license}/{date}")
    public void entranceExcel(@PathVariable("license") String[] license,@PathVariable("date") String[] date) throws ParseException {
        HashMap parkingmap =new HashMap<String,Integer>();
        System.out.println(license);
        System.out.println(date);
        for(int i=0;i<license.length;i++){
            System.out.println("parkingmap"+parkingmap);
            Parking lpr = new Parking();
            lpr.setEntrancetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date[i]));
            lpr.setCarlicense(license[i]);
            System.out.println(lpr);
            if(parkingmap.containsKey(license[i])) {//（车牌，连续出现次数）
                System.out.println("appear");
                int count =(int)parkingmap.get(license[i]);
                if(count<=3){//为了保证不受误识别的影响，连续出现三次则作为确定出现车辆。
                    parkingmap.replace(license[i],count+1);
                }else{
                    if(parkingRepository.existsByCarlicense(license[i])==0){//车辆不在停车场内
                        parkingRepository.save(lpr);
                    }
                }
            }else{
                System.out.println("not apperar");
                parkingmap.put(license[i],1);
            }
        }
        System.out.println(parkingmap);
    }

    /**
     * 上传车辆离场excel数据
     * @param license
     * @param date
     */
    @ApiOperation("上传车辆离场excel数据")
    @GetMapping("/uploadExitLpr/{license}/{date}")
    public void exitExcel(@PathVariable("license") String[] license,@PathVariable("date") String[] date) throws ParseException {
        for(int i =0;i<date.length;i++){
            System.out.println(date[i]);
        }
        HashMap parkingmap =new HashMap<String,Integer>();
        for(int i=0;i<license.length;i++){
            if(parkingmap.containsKey(license[i])) {//（车牌，连续出现次数）
                System.out.println("appear");
                int count =(int)parkingmap.get(license[i]);
                if(count<=3){//为了保证不受误识别的影响，连续出现三次则作为确定出现车辆。
                    parkingmap.replace(license[i],count+1);
                }else{
                    if(parkingRepository.existsByCarlicense(license[i])==1){//车辆在停车场内
                        int id = parkingRepository.findByCarlicense(license[i]).getParkingid();
                        Date entranceTime = parkingRepository.findByCarlicense(license[i]).getEntrancetime();
                        parkingRepository.deleteById(id);
                        //在停车记录表中增加本次停车数据
                        /***
                         * license[i]车牌->carid,entranceTime进入时间,date[i]离开时间,fare车费
                         */
                        Date exitTime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date[i]);

                        BigDecimal fare;
                        int days = (int) ((exitTime.getTime() - entranceTime.getTime()) / (1000 * 60 * 60 * 24));
                        System.out.println(days);
                        if(days>0){
                            BigDecimal day = new BigDecimal(days);
                            BigDecimal price = new BigDecimal(10);
                            fare= price.multiply(day);
                            if(fare.doubleValue()>50){
                                fare = new BigDecimal(50);
                            }
                        }else{

                            int hoursDiff = (int) ((exitTime.getTime() - entranceTime.getTime()) / (1000 * 60 * 60));
                            if(hoursDiff>1){
                                fare = new BigDecimal(5);
                            }else{
                                fare = new BigDecimal(0);
                            }
                        }
                        ParkRecord parkRecord = new ParkRecord();
                        parkRecord.setCarid(carRepository.getCarid(license[i]));
                        parkRecord.setEntrancetime(entranceTime);
                        parkRecord.setExittime(exitTime);
                        parkRecord.setFare(fare);
                        System.out.println(exitTime);
                        System.out.println("--------------------------------------------");
                        System.out.println("出场："+license[i]);
                        System.out.println("--------------------------------------------");
                        System.out.println(parkRecord);
                        parkRecordRepository.save(parkRecord);
                        UpdateBalance(carRepository.getCarid(license[i]),fare);
                    }
                }
            }else{
                System.out.println("not apperar");
                parkingmap.put(license[i],1);
            }
        }

    }
    public int UpdateBalance(int carid,BigDecimal fare){
        int userid=carRepository.getUserid(carid);
        BigDecimal balance = userRepository.getBalance(userid);
        BigDecimal current = balance.subtract(fare);
        if(current.doubleValue()>0){
            userRepository.updateBalance(userid,current);
            return 1;
        }else{
            return 0;
        }
    }


}
