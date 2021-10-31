package com.example.demo.controller;

import com.example.demo.entity.Parking;
import com.example.demo.entity.ParkingDetail;
import com.example.demo.entity.ResultResponse;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.ParkingRepository;
import com.example.demo.utils.Constants;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Elodie
 * @Date: 2021/10/17 17:59
 */
@ApiOperation(value = "停车场内数据")
@RestController
@RequestMapping("/parking")
public class ParkingHandle {
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private CarRepository carRepository;
    /***
     * 获取正在使用中的车位
     * @return
     */
    @ApiOperation(value = "获取正在使用中的车位数")
    @GetMapping("/getCount")
    public Integer getCount(){ return parkingRepository.getCount(); }
    @GetMapping("/findAll/{page}/{size}")
    public Page<Parking> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return parkingRepository.findAll(pageable);
    }
    @ApiOperation(value = "获取正在使用车位的信息",notes = "多表查询，ParkingDetail")
    @GetMapping("/findAll1/{page}/{size}")
    public Page<ParkingDetail> findAll1(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable1= PageRequest.of(page,size);
        return parkingRepository.findAll1(pageable1);
    }
    @GetMapping("/findParkingDetailByCarlicense/{carlicense}")
    public ResultResponse findParkingDetailByCarlicense(@PathVariable("carlicense") String carlicense){
        ResultResponse resultResponse = new ResultResponse();
        ParkingDetail detail = parkingRepository.findParkingDetailByCarlicense(carlicense);
        if(detail!=null){
            resultResponse.setMessage("success");
            resultResponse.setData(detail);
            resultResponse.setCode(Constants.STATUS_OK);
            return resultResponse;
        }else{
            resultResponse.setMessage("该车辆尚未进停车场");
            resultResponse.setData("fail");
            resultResponse.setCode(Constants.STATUS_FAIL);
            return resultResponse;
        }
    }
    @PostMapping("/addParkingRecord")
    public ResultResponse addParkingRecord(@RequestBody Parking parking){
        ResultResponse resultResponse = new ResultResponse();
        Integer carid =carRepository.getCarid(parking.getCarlicense());
        if(carid == null){
            resultResponse.setMessage("车牌不存在");
            resultResponse.setData("fail");
            resultResponse.setCode(Constants.STATUS_FAIL);
            return resultResponse;
        }else{
            Parking result = parkingRepository.save(parking);
            if(result != null){
                resultResponse.setMessage("success");
                resultResponse.setData("success");
                resultResponse.setCode(Constants.STATUS_OK);
                return resultResponse;
            }else{
                resultResponse.setMessage("fail");
                resultResponse.setData("fail");
                resultResponse.setCode(Constants.STATUS_FAIL);
                return resultResponse;
            }
        }


    }
    @DeleteMapping("/deleteById/{id}")
    public void deletecarById(@PathVariable("id") Integer id){
        parkingRepository.deleteById(id);
    }

}
