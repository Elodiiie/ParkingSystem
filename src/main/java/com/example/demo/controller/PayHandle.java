package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.annotation.SystemLogAspect;
import com.example.demo.vo.PayDetail;
import com.example.demo.vo.ResultResponse;
import com.example.demo.entity.Pay;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.example.demo.utils.Constants.MESSAGE_FAIL;

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

    private final static String LACK_OF_BALANCE = "Lack of Balance";
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);
    @GetMapping("/findAll/{page}/{size}")
    public ResultResponse findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,payRepository.find(pageable));
    }
    @SystemLog("添加缴费记录")
    @ApiOperation(value = "添加缴费记录")
    @PostMapping("/addRecord")
    @Transactional(rollbackFor = Exception.class)
    public ResultResponse save(@RequestBody Pay pay){
        try {
            payRepository.save(pay);
            int userId= pay.getUserid();
            BigDecimal old_balance=userRepository.getBalance(userId);
            BigDecimal new_balance=old_balance.add(new BigDecimal(pay.getFare()));
            userRepository.updateBalance(userId,new_balance);
        }catch (Exception e){
            logger.error("异常=====" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultResponse(Constants.BUSINESS_FAIL,String.valueOf(e),MESSAGE_FAIL);
        }
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
    }
    @SystemLog("修改缴费记录")
    @ApiOperation(value = "修改缴费记录")
    @PostMapping("/updateRecord")
    @Transactional(rollbackFor = Exception.class)
    public ResultResponse update(@RequestBody Pay pay){
        try {
            double old_fare=payRepository.getFareByPayid(pay.getPayid());
            int userId= pay.getUserid();
            BigDecimal old_balance=userRepository.getBalance(userId);
            BigDecimal new_balance=old_balance.add(new BigDecimal(pay.getFare()-old_fare));
            BigDecimal zero=new BigDecimal(0);
            if(new_balance.compareTo(zero)>-1){
                Pay result = payRepository.save(pay);
                Integer res = userRepository.updateBalance(userId,new_balance);
            }else{
                return new ResultResponse(Constants.BUSINESS_FAIL,LACK_OF_BALANCE,MESSAGE_FAIL);
            }
        }catch (Exception e){
            logger.error("异常=====" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultResponse(Constants.BUSINESS_FAIL,String.valueOf(e),MESSAGE_FAIL);
        }
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
    }
    @DeleteMapping("/deleteById/{id}")
    public ResultResponse deleteById(@PathVariable("id") Integer id){
        payRepository.deleteById(id);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Constants.MESSAGE_OK);
    }
    @GetMapping("/findById/{id}")
    public ResultResponse findById(@PathVariable("id") Integer id){
         return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,payRepository.findById(id).get());
    }
    @GetMapping("/getPayDetialBywx_overall/{userid}")
    public ResultResponse getPayDetialBywx_overall(@PathVariable("userid") Integer userid){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,payRepository.getByUserid(userid));
    }

}
