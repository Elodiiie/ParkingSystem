package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.annotation.SystemLogAspect;
import com.example.demo.entity.Car;
import com.example.demo.vo.CarDetail_devtool;
import com.example.demo.entity.Parking;
import com.example.demo.vo.ParkingDetail;
import com.example.demo.vo.ResultResponse;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.ParkingRepository;
import com.example.demo.utils.Constants;
import com.zhenzi.sms.ZhenziSmsClient;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.utils.Constants.MESSAGE_FAIL;
import static com.example.demo.utils.Constants.MESSAGE_OK;

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

    private final static String WRONG_LICENSE = "WRONG LICENSE or UNREGISTERED";
    private final static String NOT_IN_PARKING = "Not in the parking lot";
    private final static String IN_PARKING = "The car is already in the parking lot";
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);
    ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "110280", "90e77681-d2e7-4b9a-8f81-f90694ff2c5e");
    /***
     * 获取正在使用中的车位
     * @return
     */
    @SystemLog("获取正在使用中的车位数")
    @ApiOperation(value = "获取正在使用中的车位数")
    @GetMapping("/getCount")
    public ResultResponse getCount(){
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,parkingRepository.getCount());
    }

    @ApiOperation(value = "获取正在使用车位的信息",notes = "多表查询，ParkingDetail")
    @GetMapping("/findAll/{page}/{size}")
    public ResultResponse findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,parkingRepository.findAll1(pageable));
    }
    @GetMapping("/findParkingDetailByCarLicense/{carLicense}")
    public ResultResponse findParkingDetailByCarLicense(@PathVariable("carLicense") String carlicense){
        Integer carId = carRepository.getCarid(carlicense);
        if(carId == null) {
            return new ResultResponse(Constants.BUSINESS_FAIL,WRONG_LICENSE,MESSAGE_FAIL);
        }
        ParkingDetail detail = parkingRepository.findParkingDetailByCarlicense(carlicense);
        if(detail!=null){
            return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,detail);
        }else{
            return new ResultResponse(Constants.BUSINESS_FAIL,NOT_IN_PARKING,MESSAGE_FAIL);
        }
    }
    @SystemLog("添加停车记录")
    @PostMapping("/addParkingRecord")
    public ResultResponse addParkingRecord(@RequestBody Parking parking) throws Exception {
        Integer carId =carRepository.getCarid(parking.getCarlicense());
        if(carId == null){
            return new ResultResponse(Constants.BUSINESS_FAIL,WRONG_LICENSE,Constants.MESSAGE_FAIL);
        }else{
            if(parkingRepository.existsByCarlicense(parking.getCarlicense())){
                return new ResultResponse(Constants.BUSINESS_FAIL,IN_PARKING,Constants.MESSAGE_FAIL);
            }
            Parking result = parkingRepository.save(parking);
            if(result != null){
                return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,MESSAGE_OK);
            }else{
                return new ResultResponse(Constants.STATUS_OK, MESSAGE_FAIL,MESSAGE_FAIL);
            }
        }
    }


    @PostMapping("sendMess")
    public ResultResponse sendMess(@RequestBody Parking parking) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", "17521344145");//TODO 更换为用户的手机号
        params.put("templateId", "7168");
        String[] templateParams = new String[3];
        templateParams[0] = parking.getCarlicense();
        templateParams[1] = String.valueOf(parking.getEntrancetime().toLocaleString()).substring(5,9);
        templateParams[2] = String.valueOf(parking.getEntrancetime().toLocaleString()).substring(10);
        params.put("templateParams", templateParams);
        String result_email = client.send(params);
        logger.info("result_email"+result_email);
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,Boolean.TRUE);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResultResponse deleteParkingById(@PathVariable("id") Integer id){
        parkingRepository.deleteById(id);
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,Boolean.TRUE);
    }

    @GetMapping("/getCarDetialBywx_overall/{userid}")
    public List<CarDetail_devtool> getCarDetialBywx_overall(@PathVariable("userid") Integer userid){
        List<CarDetail_devtool> list = new ArrayList<>();
        List<Car> car_list = carRepository.findByUserid(userid);
        for(Car car : car_list){
            if(carRepository.findByCarlicenseEqualsAndAndExistToUserEquals(car.getCarlicense(),1)==0){
                continue;
            }
            if(parkingRepository.existsByCarlicense(car.getCarlicense())){
                list.add(parkingRepository.getCarDetailByWx_in(car.getCarlicense()));
            }else{
                list.add(parkingRepository.getCarDetailByWx_out(car.getCarlicense()));
            }
        }
        return list;
    }

}
