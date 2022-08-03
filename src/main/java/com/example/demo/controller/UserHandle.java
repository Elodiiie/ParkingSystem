package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.dto.Balance;
import com.example.demo.dto.Password;
import com.example.demo.dto.UserAndPhone;
import com.example.demo.entity.User;
import com.example.demo.repository.AccountSessionRepository;
import com.example.demo.vo.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Elodie
 * @Date: 2021/6/19 19:19
 */
@Api(value = "用户信息",description="用户信息")
@RestController
@RequestMapping("/user")
public class UserHandle {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountSessionRepository accountSessionRepository;
    @Autowired
    HttpServletRequest request;
    private final static String WRONG_PHONE = "Wrong Number or Not Unregistered";
    @SystemLog("分页查找user信息")
    @ApiOperation("分页查找")
    @GetMapping("/findAll/{page}/{size}")
    public ResultResponse findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,userRepository.findAll(pageable));
    }
    public Boolean  verifySession(String username,String sessionid){
        if(accountSessionRepository.existsByUseridIs(userRepository.findUseridByUsername(username))){
            String dbSessionid = accountSessionRepository.getAccountSessionByUseridIs(userRepository.findUseridByUsername(username)).getSessionid();
            if(! request.getSession().getId().equals(dbSessionid)){
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
    @ApiOperation("查找 不分页下所有消息")
    @GetMapping("/find")
    public ResultResponse find(){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,userRepository.findAll());
    }
    @PostMapping("/save")
    public ResultResponse save(@RequestBody User user){
        User result = userRepository.save(user);
        if(result != null){
            return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
        }else{
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
        }
    }
    @GetMapping("/findByPhoneIsLike/{phone}")
    public ResultResponse findByPhoneIsLike(@PathVariable("phone") String phone){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,userRepository.findByPhoneIsLike(phone+"%"));
    }
    @ApiOperation("根据id查找")
    @GetMapping("/findById/{id}")
    public ResultResponse findById(@PathVariable("id") Integer id){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,userRepository.findById(id).get());
    }

    @ApiOperation("根据手机号查找")
    @GetMapping("/findByPhone/{phone}")
    public ResultResponse findByPhone(@PathVariable("phone") String phone){
        if(userRepository.existsByPhoneIs(phone)){
            return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,userRepository.findByPhone(phone));
        }
        return new ResultResponse(Constants.BUSINESS_FAIL,WRONG_PHONE,Boolean.FALSE);
    }
    @GetMapping("/getBalance/{userId}")
    public ResultResponse findByPhone(@PathVariable("userId") int userId){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,userRepository.getBalance(userId));
    }
    @PostMapping("/updateBalance")
    public ResultResponse updateBalance(@RequestBody Balance balance) {
        int result = userRepository.updateBalance(balance.getUserid(),balance.getBalance());
        if(result==1){
            return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
        }else{
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
        }
    }
    @PostMapping("/updateUser_password")
    public ResultResponse updateUser_password(@RequestBody Password password){
        Integer result = userRepository.updatePassword(password.getUserid(),DigestUtils.md5DigestAsHex(password.getPassword().getBytes()));
        if(result == 1){
            return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
        }else{
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
        }
    }
    @DeleteMapping("/deleteUserById/{id}")
    public ResultResponse deleteUserById(@PathVariable("id") Integer id){
        userRepository.deleteById(id);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
    }
    /***
     * 获取用户数量
     * @return
     */
    @ApiOperation(value = "获取用户数量")
    @GetMapping("/getCount")
    public ResultResponse getCount(){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,userRepository.getCount());
    }
    @ApiOperation("判断用户是否存在")
    @PostMapping("/existByPhoneAndUsername")
    public ResultResponse login(@RequestBody UserAndPhone user  ){
        try {
            int flag = userRepository.verifyPhoneAndUsername(user.getUsername(),user.getPhone());
            if(flag != 0){
                return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
            }else{
                return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
            }
        }catch(Exception e){
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
        }
    }
}
