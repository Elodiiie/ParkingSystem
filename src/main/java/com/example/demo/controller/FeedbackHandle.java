package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.entity.Feedback;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.utils.Constants;
import com.example.demo.vo.FeedbackVo;
import com.example.demo.vo.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Elodie
 * @Date: 2021/11/18 15:28
 */
@Api(value = "反馈信息",description="反馈信息")
@RestController
@RequestMapping("/feedback")
public class FeedbackHandle {
    public final static int SOLVING = 0;
    public final static int SOLVED = 2;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @SystemLog("分页查找feedback信息")
    @ApiOperation("分页查找")
    @GetMapping("/findAll/{page}/{size}")
    public ResultResponse findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,feedbackRepository.findAll1(pageable));
    }

    @SystemLog("分页查找未处理feedback信息")
    @ApiOperation("分页查找")
    @GetMapping("/findNotOperate/{page}/{size}")
    public ResultResponse findNotOperate(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,feedbackRepository.findByIsRead(pageable,0));
    }

    @SystemLog("分页查找已处理feedback信息")
    @ApiOperation("分页查找")
    @GetMapping("/findOperate/{page}/{size}")
    public ResultResponse findOperate(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,feedbackRepository.findByIsRead(pageable,2));
    }

    @ApiOperation(value = "添加反馈信息 ")
    @PostMapping("/saveAll")
    public ResultResponse save(@RequestBody Feedback feedback){
        Feedback result = feedbackRepository.save(feedback);
        if(result != null){
            return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
        }else{
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
        }
    }

    @ApiOperation(value = "修改为已解决")
    @GetMapping("/processed/{id}")
    public ResultResponse processed(@PathVariable("id") Integer feedbackid){
        return update(feedbackid, SOLVED);
    }

    @ApiOperation(value = "修改为未解决")
    @GetMapping("/processing/{id}")
    public ResultResponse processing(@PathVariable("id") Integer feedbackid){
        return update(feedbackid, SOLVING);
    }

    private ResultResponse update(@PathVariable("id") Integer feedbackid, int solved) {
        Feedback feedback =feedbackRepository.getById(feedbackid);
        feedback.setIsRead(solved);
        feedbackRepository.save(feedback);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
    }

    @ApiOperation(value = "查看详情")
    @GetMapping("/see_details/{id}")
    public ResultResponse see_details(@PathVariable("id") Integer feedbackid){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,feedbackRepository.getById(feedbackid));
    }
}
