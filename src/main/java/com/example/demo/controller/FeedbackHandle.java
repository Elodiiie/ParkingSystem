package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.entity.Feedback;
import com.example.demo.repository.FeedbackRepository;
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
    @Autowired
    private FeedbackRepository feedbackRepository;
    @SystemLog("分页查找feedback信息")
    @ApiOperation("分页查找")
    @GetMapping("/findAll/{page}/{size}")
    public Page<Feedback> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return feedbackRepository.findAll(pageable);
    }
    @ApiOperation(value = "添加反馈信息 ")
    @PostMapping("/saveAll")
    public Boolean save(@RequestBody Feedback feedback){
        Feedback result = feedbackRepository.save(feedback);
        if(result != null){
            return true;
        }else{
            return false;
        }
    }
}
