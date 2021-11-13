package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.entity.EntranceCount;
import com.example.demo.entity.ExitCount;
import com.example.demo.entity.FareCount;
import com.example.demo.repository.ParkRecordRepository;
import com.example.demo.repository.PayRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Elodie
 * @Date: 2021/10/16 22:59
 */
@Api(value = "图数据")
@RestController
@RequestMapping("/echarts")
public class EchartsHandle {
    @Autowired
    private ParkRecordRepository parkRecordRepository;
    @Autowired
    private PayRepository payRepository;

    @ApiOperation(value = "获取当前日期的前六个月")
    @GetMapping("/getSixMonths")
    public List<String> currentMonth6(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String dateString = sdf.format(cal.getTime());
        List<String> rqList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            dateString = sdf.format(cal.getTime());
            String month = dateString.substring(0,7);
            rqList.add(month);
            cal.add(Calendar.MONTH, -1);
        }
        Collections.reverse(rqList);
        return rqList;
    }
    @SystemLog("获取图数据进场车辆信息")
    @ApiOperation(value = "获取图数据进场车辆信息")
    @GetMapping("/recordentrancecount")
    public List<Integer> findEntranceCount(){
        List<String> monthLists = currentMonth6();
        List<EntranceCount> list = parkRecordRepository.getEntranceCount();
        List<Integer> countlist = new ArrayList<>();
        int list_index=0;
        for(int i =0;i<6;i++){
            if(monthLists.get(i).equals(list.get(list_index).getMonths())){
                countlist.add(list.get(list_index).getEntrancecount());
                list_index++;
            }else{
                countlist.add(0);
            }
        }
        return countlist;
    }
    @SystemLog("获取图数据离场车辆信息")
    @ApiOperation(value = "获取图数据离场车辆信息")
    @GetMapping("/recordexitcount")
    public List<Integer> findExitCount(){
        List<String> monthLists = currentMonth6();
        List<ExitCount> list = parkRecordRepository.getExitCount();
        List<Integer> countlist = new ArrayList<>();
        int list_index=0;
        for(int i =0;i<6;i++){
            if(monthLists.get(i).equals(list.get(list_index).getMonths())){
                countlist.add(list.get(list_index).getExitcount());
                list_index++;
            }else{
                countlist.add(0);
            }
        }
        return countlist;
    }
    @SystemLog("获取图数据财务扣费信息")
    @ApiOperation(value = "获取图数据财务扣费信息")
    @GetMapping("/recordfarecount")
    public List<Integer> findFareCount(){
        return getfarecount(parkRecordRepository.getFareCount());
    }
    @SystemLog("获取图数据财务用户缴费信息")
    @ApiOperation(value = "获取图数据财务用户缴费信息")
    @GetMapping("/recordpaycount")
    public List<Integer> findPayCount(){
        return getfarecount(payRepository.getFareCount());
    }

    private List<Integer> getfarecount(List<FareCount> fareCount) {
        List<String> monthLists = currentMonth6();
        List<FareCount> list = fareCount;
        List<Integer> countlist = new ArrayList<>();
        int list_index=0;
        for(int i =0;i<6;i++){
            if(monthLists.get(i).equals(list.get(list_index).getMonths())){
                countlist.add(list.get(list_index).getFarecount());
                list_index++;
            }else{
                countlist.add(0);
            }
        }
        return countlist;
    }
}
