package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.entity.Points;
import com.example.demo.repository.PointsRepository;
import com.example.demo.utils.Constants;
import com.example.demo.vo.ResultResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elodie
 * @Date: 2021/11/22 20:42
 */
@Api(description = "积分信息")
@RestController
@RequestMapping("/points")
public class PointsHandle {
    @Autowired
    private PointsRepository pointsRepository;

    @SystemLog("返回所有积分信息")
    @GetMapping("/findAll/{page}/{size}")
    public Page<Points> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return pointsRepository.findAll(pageable);
    }
    public boolean isExist(int userid){
        if(pointsRepository.findByUserid(userid)!=null){
            return false;
        }else {
            return true;
        }
    }
    @SystemLog("添加积分")
    @GetMapping("/addPoints/{userid}/{points}")
    public ResultResponse addPoints(@PathVariable("userid") Integer userid, @PathVariable("points") Integer points){
        ResultResponse resultResponse = new ResultResponse();
        Points res;
       if(isExist(userid)){
           Points points1 = pointsRepository.findByUserid(userid);
           points1.setPoints(points1.getPoints()+ points);
           res = pointsRepository.save(points1);
       }else{
           Points points2 = new Points();
           points2.setPoints(points);
           points2.setUserid(userid);
           res = pointsRepository.save(points2);
       }
        if(res ==null){
            resultResponse.setMessage("添加积分失败");
            resultResponse.setData("fail");
            resultResponse.setCode(Constants.STATUS_FAIL);
        }else{
            resultResponse.setMessage("success");
            resultResponse.setData("success");
            resultResponse.setCode(Constants.STATUS_OK);
        }
        return resultResponse;
    }
    @SystemLog("删除积分")
    @GetMapping("/minusPoints/{userid}/{points}")
    public ResultResponse minusPoints(@PathVariable("userid") Integer userid, @PathVariable("points") Integer points){
        ResultResponse resultResponse = new ResultResponse();
        Points res;
        if(isExist(userid)){
            Points points1 = pointsRepository.findByUserid(userid);
            points1.setPoints(points1.getPoints()- points);
            res = pointsRepository.save(points1);
        }else{
            resultResponse.setMessage("该用户无积分，删除失败");
            resultResponse.setData("fail");
            resultResponse.setCode(Constants.STATUS_FAIL);
        }
        return resultResponse;
    }
}
