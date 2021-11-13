package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.entity.User;
import com.example.demo.entity.UserAndPhone;
import com.example.demo.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @SystemLog("分页查找user信息")
    @ApiOperation("分页查找")
    @GetMapping("/findAll/{page}/{size}")
    public Page<User> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return userRepository.findAll(pageable);
    }
    @ApiOperation("查找 不分页下所有消息")
    @GetMapping("/find")
    public List<User> find(){
        return userRepository.findAll();
    }
    @PostMapping("/save")
    public Boolean save(@RequestBody User user){
        User result = userRepository.save(user);
        if(result != null){
            return true;
        }else{
            return false;
        }
    }
    @GetMapping("/findByPhoneIsLike/{phone}")
    public List<User> findByPhoneIsLike(@PathVariable("phone") String phone){
        return userRepository.findByPhoneIsLike(phone+"%");
    }
    @ApiOperation("根据id查找")
    @GetMapping("/findById/{id}")
    public User findById(@PathVariable("id") Integer id){
        return userRepository.findById(id).get();
    }
    @ApiOperation("根据手机号查找")
    @GetMapping("/findByPhone/{phone}")
    public User findByPhone(@PathVariable("phone") String phone){
        return userRepository.findByPhone(phone);
    }
    @PutMapping("/updateuser")
    public Boolean update(@RequestBody User user){
        User result = userRepository.save(user);
        if(result != null){
            return true;
        }else{
            return false;
        }
    }
    @DeleteMapping("/deleteuserById/{id}")
    public void deleteUserById(@PathVariable("id") Integer id){
        userRepository.deleteById(id);
    }
    /***
     * 获取用户数量
     * @return
     */
    @ApiOperation(value = "获取用户数量")
    @GetMapping("/getCount")
    public Integer getCount(){ return userRepository.getCount(); }
    @ApiOperation("判断用户是否存在")
    @PostMapping("/existByPhoneAndUsername")
    public Boolean login(@RequestBody UserAndPhone user  ){
        try {
            int flag = userRepository.verifyPhoneAndUsername(user.getUsername(),user.getPhone());
            if(flag != 0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }
}
