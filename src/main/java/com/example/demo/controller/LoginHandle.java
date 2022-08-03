package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.entity.AccountSession;
import com.example.demo.repository.AccountSessionRepository;
import com.example.demo.vo.ResultResponse;
import com.example.demo.dto.UserAndPasswd;
import com.example.demo.vo.UserInfo;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;
import org.springframework.util.DigestUtils;
import com.example.demo.utils.JwtUtil;
import com.example.demo.vo.VoToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
@Api(value = "登录信息",description="登录信息")
@RestController
@Slf4j
public class LoginHandle {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountSessionRepository accountSessionRepository;

    @Autowired
    HttpServletRequest request;
    private final static String IN_USE = "Account is in use";
    private final static String WRONG_PASSWORD = "The user name and password do not match";
    @ApiOperation("用户登出")
    @PostMapping("/user/logout")
    public ResultResponse logout(@RequestHeader("X-Token") String token){
        ResultResponse res = new ResultResponse();
        // 验证token的合法和有效性
        String tokenValue = JwtUtil.verify(token);// success:zhangsan1
        // 获取token中的用户名
        String username = tokenValue.replaceFirst(JwtUtil.TOKEN_SUCCESS, "");
        // 移除session中的登录标记（或者redis中的登录标记）
        res.setMessage("logout success");
        res.setData("logout success");
        res.setCode(Constants.STATUS_OK);

        return res;
    }
    @ApiOperation(value = "获取用户基本信息",notes = "包括姓名，头像，角色，个人简介")
    @GetMapping("/user/info")
    public ResultResponse info(@RequestParam("token") String token){
        return getInfo(token);
    }
    @ApiOperation(value = "获取用户基本信息2",notes = "包括姓名，头像，角色，个人简介/微信小程序使用")
    @PostMapping("/user/info2")
    public ResultResponse info2(@RequestBody String token){
        return getInfo(token);
    }

    private ResultResponse getInfo(@RequestBody String token) {
        ResultResponse res = new ResultResponse();
        // 验证token的合法和有效性
        String tokenValue = JwtUtil.verify(token);// success:zhangsan1
        if (tokenValue != null && tokenValue.startsWith(JwtUtil.TOKEN_SUCCESS)) {
            // 如果ok-》返回需要的用户信息
            // zhangsan1
            String username = tokenValue.replaceFirst(JwtUtil.TOKEN_SUCCESS, "");
                UserInfo info = getUserInfo(username);
                System.out.println(info);
                res.setData(info);
                res.setMessage(Constants.MESSAGE_OK);
                res.setCode(Constants.STATUS_OK);
        } else {
            // 否则：500
            res.setCode(Constants.STATUS_FAIL);
            res.setMessage(Constants.MESSAGE_FAIL);
        }

        return res;
    }

    public UserInfo getUserInfo(@RequestBody String username){
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

    @SystemLog("用户登录")
    @ApiOperation("用户登录")
    @PostMapping("/user/login")
    public ResultResponse login(@RequestBody UserAndPasswd user ){
        try {
            int flag = userRepository.verifyPassword(user.getUsername(),DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            if(flag != 0){
                int userid=userRepository.getUserByUsernameIsAndPasswordIs(user.getUsername(),DigestUtils.md5DigestAsHex(user.getPassword().getBytes())).getUserid();
                verifySessionAdd(request.getSession().getId(),userid);
                // 创建一个token数据，封装到res对象中
                userRepository.updateLastLoginTimeByUsername(user.getUsername());
                String token = JwtUtil.sign(user.getUsername(), ""+userid);
                return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,new VoToken(token));
            }else{
                return new ResultResponse(Constants.STATUS_FAIL,WRONG_PASSWORD,Boolean.FALSE);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL+e.getMessage(),Boolean.FALSE);
        }

    }

    public void verifySessionAdd(String sessionid,int userid){
        AccountSession accountSession = new AccountSession();
        accountSession.setSessionid(sessionid);
        accountSession.setUserid(userid);
        System.out.println(sessionid);
        if(accountSessionRepository.existsByUseridIs(userid)){
            accountSession.setAccSessId(accountSessionRepository.getAccountSessionByUseridIs(userid).getAccSessId());
        }
        accountSessionRepository.save(accountSession);
    }

    @SystemLog("验证旧密码正确")
    @ApiOperation("验证旧密码正确")
    @PostMapping("/user/verifyPassword")
    public ResultResponse verifyPassword(@RequestBody UserAndPasswd user ){
        try {
            int flag = userRepository.verifyPassword(user.getUsername(),DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            if(flag != 0){
                return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
            }else{
                return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
        }
    }
}
