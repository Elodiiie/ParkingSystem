package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.utils.Constants;
import com.example.demo.vo.EntranceCount;
import com.example.demo.vo.ExitCount;
import com.example.demo.vo.FareCount;
import com.example.demo.repository.ParkRecordRepository;
import com.example.demo.repository.PayRepository;
import com.example.demo.vo.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/charts")
public class EchartsHandle {
    @Autowired
    private ParkRecordRepository parkRecordRepository;
    @Autowired
    private PayRepository payRepository;

    @ApiOperation(value = "获取当前日期的前六个月")
    @GetMapping("/getSixMonths")
    public ResultResponse currentMonth6(){
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
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,rqList);
    }

    @SystemLog("获取图数据进场车辆信息")
    @ApiOperation(value = "获取图数据进场车辆信息")
    @GetMapping("/recordEntranceCount")
    public ResultResponse findEntranceCount(){
        List<String> monthLists = (List<String>) currentMonth6().getData();
        List<EntranceCount> list = parkRecordRepository.getEntranceCount();
        List<Integer> countlist = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            boolean flag =Boolean.FALSE;//未找到当月数据
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getMonths().equals(monthLists.get(i))) {
                    flag=Boolean.TRUE;
                    countlist.add(list.get(j).getEntrancecount());
                    break;
                }
            }
            if(!flag){
                countlist.add(0);
            }
        }
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,countlist);
    }

    @SystemLog("获取图数据离场车辆信息")
    @ApiOperation(value = "获取图数据离场车辆信息")
    @GetMapping("/recordExitCount")
    public ResultResponse findExitCount(){
        List<String> monthLists = (List<String>) currentMonth6().getData();
        List<ExitCount> list = parkRecordRepository.getExitCount();
        List<Integer> countlist = new ArrayList<>();
        for(int i =0;i<6;i++){
            boolean flag =Boolean.FALSE;//未找到当月数据
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getMonths().equals(monthLists.get(i))) {
                    flag=Boolean.TRUE;
                    countlist.add(list.get(j).getExitcount());
                    break;
                }
            }
            if(!flag){
                countlist.add(0);
            }
        }
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,countlist);
    }

    @SystemLog("获取图数据财务扣费信息")
    @ApiOperation(value = "获取图数据财务扣费信息")
    @GetMapping("/recordFareCount")
    public ResultResponse findFareCount(){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,getfarecount(parkRecordRepository.getFareCount()));
    }

    @SystemLog("获取图数据财务用户缴费信息")
    @ApiOperation(value = "获取图数据财务用户缴费信息")
    @GetMapping("/recordPayCount")
    public ResultResponse findPayCount(){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,getfarecount(payRepository.getFareCount()));
    }

    private List<Integer> getfarecount(List<FareCount> fareCount) {
        List<String> monthLists = (List<String>) currentMonth6().getData();
        List<FareCount> list = fareCount;
        List<Integer> countlist = new ArrayList<>();
        for(int i =0;i<6;i++){
            boolean flag =Boolean.FALSE;//未找到当月数据
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getMonths().equals(monthLists.get(i))) {
                    flag=Boolean.TRUE;
                    countlist.add(list.get(j).getFarecount());
                    break;
                }
            }
            if(!flag){
                countlist.add(0);
            }
        }
        return countlist;
    }
}
