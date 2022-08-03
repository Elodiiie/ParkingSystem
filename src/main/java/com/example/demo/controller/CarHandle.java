package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;
import com.example.demo.vo.CarDetail;
import com.example.demo.entity.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.vo.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 13:52
 */

@Api(description = "车辆信息")
@RestController
@RequestMapping("/car")
public class CarHandle {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    private final static String ACCOUNT_UNREGISITERED = "The account does not exist";
    @GetMapping("/find")
    public ResultResponse find(){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.find());
    }
    /**
     * 返回所有车辆数据
     * @return
     */
    @SystemLog("返回所有车辆数据")
    @GetMapping("/findAll/{page}/{size}")
    public ResultResponse findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.findAll(pageable));
    }

    @SystemLog("获取车辆记录信息1")
    @ApiOperation(value = "获取车辆记录信息1",notes ="多表查询，返回CarDetail")
    @GetMapping("/findAll1/{page}/{size}")
    public ResultResponse findAll1(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.findAll1(pageable));
    }

    @ApiOperation(value="根据用户id查找该用户所有车辆")
    @GetMapping("/getCarLicenseByUserId/{userId}")
    public ResultResponse getCarLicenseByUserId(@PathVariable("userId") Integer id){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.findByUserid(id).stream().map(Car::getCarlicense).collect(Collectors.toList()));
    }

    @ApiOperation(value="根据手机号查找该用户所有车辆")
    @GetMapping("/getCarLicenseByPhone/{phone}")
    public ResultResponse getCarLicenseByPhone(@PathVariable("phone") String phone){
        if(userRepository.existsByPhoneIs(phone)){
            User user = userRepository.findByPhone(phone);
            return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.findByUserid(user.getUserid()).stream().map(Car::getCarlicense).collect(Collectors.toList()));
        }
        return new ResultResponse(Constants.STATUS_FAIL,ACCOUNT_UNREGISITERED,Boolean.FALSE);
    }

    @ApiOperation(value="根据用户id查找所拥有的车辆数")
    @GetMapping("/getNumByUserId/{id}")
    public ResultResponse getNumByUserId(@PathVariable("id") Integer id){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.countByUserid(id));
    }

    @ApiOperation(value="根据用户id查找所拥有的车辆数(除去用户删除数据)")
    @GetMapping("/countByUserIdByUser/{id}")
    public ResultResponse countByUseridByUser(@PathVariable("id") Integer id){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.countByUseridByUser(id));
    }

    @ApiOperation(value="根据车辆id查找",notes ="多表")
    @GetMapping("/findDetailByCarId/{id}")
    public ResultResponse findDetailByCarId(@PathVariable("id") Integer id){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.findDetailByCarid(id));
    }

    @ApiOperation(value="根据车牌号查找",notes ="<多表>")
    @GetMapping("/findByCarLicense/{license}")
    public ResultResponse findDetailByCarlicense(@PathVariable("license") String license){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.findDetailByCarlicense(license));
    }

    @ApiOperation(value = "增修车辆信息 ")
    @PostMapping("/saveAll")
    public ResultResponse save(@RequestBody Car car){
        Car result = carRepository.save(car);
        if(result != null){
            return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
        }else{
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
        }
    }

    @GetMapping("/findById/{id}")
    public ResultResponse findById(@PathVariable("id") Integer id){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.findById(id).get());
    }


    @DeleteMapping("/deleteCarById/{id}")
    public ResultResponse deleteCarById(@PathVariable("id") Integer id){
        carRepository.deleteById(id);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
    }

    @DeleteMapping("/deleteCarByIdByUser/{id}")
    public ResultResponse deleteCarByIdByUser(@PathVariable("id") Integer id){
        Car car = carRepository.findByCaridIs(id);
        car.setExistToUser(0);
        carRepository.save(car);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
    }
    /***
     * 获取车辆数量
     * @return
     */
    @SystemLog("获取车辆数量")
    @ApiOperation(value = "获取车辆数量")
    @GetMapping("/getCount")
    public ResultResponse getCount(){ return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,carRepository.getCount()); }
}
