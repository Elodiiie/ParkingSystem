package com.example.demo.controller;

import com.example.demo.entity.Car;
import com.example.demo.entity.CarDetail;
import com.example.demo.repository.CarRepository;
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
 * @Date: 2021/6/20 13:52
 */
@Api(description = "车辆信息")
@RestController
@RequestMapping("/car")
public class CarHandle {
    @Autowired
    private CarRepository carRepository;

    /**
     * 返回所有车辆数据
     * @return
     */
    @GetMapping("/findAll/{page}/{size}")
    public Page<Car> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return carRepository.findAll(pageable);
    }
    @ApiOperation(value = "获取车辆记录信息1",notes ="多表查询，返回CarDetail")
    @GetMapping("/findAll1/{page}/{size}")
    public Page<CarDetail> findAll1(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable1= PageRequest.of(page,size);
        return carRepository.findAll1(pageable1);
    }
    //    @GetMapping("/findAll")
//    public List<Car> findAll(){
//        return carRepository.findAll();
//    }
    @ApiOperation(value="根据id查找",notes ="<多表>")
    @GetMapping("/findDetailByCarid/{id}")
    public CarDetail findDetailByCarid(@PathVariable("id") Integer id){
        return carRepository.findDetailByCarid(id);
    }
    @ApiOperation(value="根据车牌号查找",notes ="<多表>")
    @GetMapping("/findByCarlicense/{license}")
        public CarDetail findDetailByCarlicense(@PathVariable("license") String license){ return carRepository.findDetailByCarlicense(license); }
    /**
     * 添加车辆数据
     * @param car
     * @return
     */
    @ApiOperation(value = "添加车辆信息 ")
    @PostMapping("/saveAll")
    public Boolean save(@RequestBody Car car){
        Car result = carRepository.save(car);
        if(result != null){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 根据车辆id查询车辆信息
     * @param id 车辆编号
     * @return Car
     */
    @GetMapping("/findById/{id}")
    public Car findById(@PathVariable("id") Integer id){
        return carRepository.findById(id).get();
    }

    /**
     * 更新车辆信息
     * @param car
     * @return
     */
    @PutMapping("/updatecar")
    public Boolean update(@RequestBody Car car){
        Car result = carRepository.save(car);
        if(result != null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据车辆id删除车辆信息   （车牌
     * @param id
     */
    @DeleteMapping("/deletecarById/{id}")
    public void deletecarById(@PathVariable("id") Integer id){
        carRepository.deleteById(id);
    }

    /***
     * 获取车辆数量
     * @return
     */
    @ApiOperation(value = "获取车辆数量")
    @GetMapping("/getCount")
    public Integer getCount(){ return carRepository.getCount(); }
}