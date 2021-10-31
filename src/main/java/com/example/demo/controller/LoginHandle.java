package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;

import com.example.demo.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@Api(value = "登录信息",description="登录信息")
@RestController
@Slf4j
public class LoginHandle {
    @Autowired
    private UserRepository userRepository;
    @ApiOperation("用户登出")
    @PostMapping("/user/logout")
    public ResultResponse logout(@RequestHeader("X-Token") String token){
        ResultResponse res = new ResultResponse();
        // 验证token的合法和有效性
        String tokenValue = JwtUtil.verity(token);// success:zhangsan1
        // 获取token中的用户名
        String username = tokenValue.replaceFirst(JwtUtil.TOKEN_SUCCESS, "");
        // 移除session中的登录标记（或者redis中的登录标记）
        res.setMessage("logout success");
        res.setData("logout success");
        res.setCode(Constants.STATUS_OK);

        return res;
    }

    public UserInfo getUserInfo(String username){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(username);
        userInfo.setUserid(userRepository.findUseridByUsername(username));
        userInfo.setAvatar(userRepository.findAvatarByname(username));
        userInfo.setIntroduction(userRepository.findIntroductionByname(username));
        List<String> roles;
        if(userRepository.findRolesByname(username).equals("admin")){
            roles = Arrays.asList("admin","editor");
        }else{
            roles = Arrays.asList("editor");
        }
        userInfo.setRoles(roles);
        return userInfo;
    }
    @ApiOperation(value = "获取用户基本信息",notes = "包括姓名，头像，角色，个人简介")
    @GetMapping("/user/info")
    public ResultResponse info(@RequestParam("token") String token){
        ResultResponse res = new ResultResponse();
        // 验证token的合法和有效性
        String tokenValue = JwtUtil.verity(token);// success:zhangsan1
        if(tokenValue != null && tokenValue.startsWith(JwtUtil.TOKEN_SUCCESS)) {
            // 如果ok-》返回需要的用户信息
            // zhangsan1
            String username = tokenValue.replaceFirst(JwtUtil.TOKEN_SUCCESS, "");

            UserInfo info = getUserInfo(username);
            System.out.println(info);
            res.setData(info);
            res.setMessage(Constants.MESSAGE_OK);
            res.setCode(Constants.STATUS_OK);
        }else {
            // 否则：500
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }

        return res;
    }
    @ApiOperation("用户登录")
    @PostMapping("/user/login")
    public ResultResponse login(@RequestBody UserAndPasswd user  ){
        ResultResponse res = new ResultResponse();
        try {
            int flag = userRepository.verifyPassword(user.getUsername(),user.getPassword());

            if(flag != 0){
                // 创建一个token数据，封装到res对象中
                userRepository.updateLastLoginTimeByUsername(user.getUsername());
                String token = JwtUtil.sign(user.getUsername(), "-1");
                res.setCode(Constants.STATUS_OK);
                res.setMessage(Constants.MESSAGE_OK);
                res.setData(new VoToken(token));
            }else{
                res.setCode(Constants.STATUS_FAIL);
                res.setMessage(Constants.MESSAGE_FAIL+"username_and_password_do_not_match");
                res.setData("fail");
            }
        }catch(Exception e){
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL+e.getMessage());
            res.setData("fail");
            e.printStackTrace();
        }
        return res;
    }

}
