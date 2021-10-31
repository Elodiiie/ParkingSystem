package com.example.demo.controller;

import com.example.demo.entity.Pay;
import com.example.demo.entity.ResultResponse;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/10/20 21:21
 */
@Api(description = "缴费信息")
@RestController
@RequestMapping("/pay")
public class PayHandle {
    @Autowired
    private PayRepository payRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/findAll/{page}/{size}")
    public Page<Pay> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return payRepository.findAll(pageable);
    }
    @ApiOperation(value = "添加缴费记录")
    @PostMapping("/addRecord")
    @Transactional(rollbackFor = Exception.class)
    public ResultResponse save(@RequestBody Pay pay){
        ResultResponse resultResponse = new ResultResponse();
        try {
            Pay result = payRepository.save(pay);
            int userid= userRepository.findUseridByUsername(pay.getUsername());
            BigDecimal old_balance=userRepository.getBalance(userid);
            BigDecimal new_balance=old_balance.add(new BigDecimal(pay.getFare()));
            Integer res = userRepository.updateBalance(userid,new_balance);
        }catch (Exception e){
            System.out.println("异常=====" + e);
            //手动强制回滚事务，这里一定要第一时间处理
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resultResponse.setMessage("异常，交易失败"+e);
            resultResponse.setData("fail");
            resultResponse.setCode(Constants.STATUS_FAIL);
            return resultResponse;
        }
        resultResponse.setMessage("success");
        resultResponse.setData("success");
        resultResponse.setCode(Constants.STATUS_OK);
        return resultResponse;
    }
    @ApiOperation(value = "修改缴费记录")
    @PostMapping("/updateRecord")
    @Transactional(rollbackFor = Exception.class)
    public ResultResponse update(@RequestBody Pay pay){
        ResultResponse resultResponse = new ResultResponse();
        try {
            double old_fare=payRepository.getFareByPayid(pay.getPayid());
            int userid= userRepository.findUseridByUsername(pay.getUsername());
            BigDecimal old_balance=userRepository.getBalance(userid);
            BigDecimal new_balance=old_balance.add(new BigDecimal(pay.getFare()-old_fare));
            BigDecimal zero=new BigDecimal(0);
            if(new_balance.compareTo(zero)>-1){
                Pay result = payRepository.save(pay);
                Integer res = userRepository.updateBalance(userid,new_balance);
            }else{
                resultResponse.setMessage("账户余额不足，请重新继续缴费，交易失败");
                resultResponse.setData("fail");
                resultResponse.setCode(Constants.STATUS_FAIL);
                return resultResponse;
            }
        }catch (Exception e){
            System.out.println("异常=====" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resultResponse.setMessage("error"+e);
            resultResponse.setData("fail");
            resultResponse.setCode(Constants.STATUS_FAIL);
            return resultResponse;
        }
        resultResponse.setMessage("success");
        resultResponse.setData("success");
        resultResponse.setCode(Constants.STATUS_OK);
        return resultResponse;
    }
    @DeleteMapping("/deleteById/{id}")
    public void deletecarById(@PathVariable("id") Integer id){
        payRepository.deleteById(id);
    }
    @GetMapping("/findById/{id}")
    public Pay findById(@PathVariable("id") Integer id){
        return payRepository.findById(id).get();
    }

}
