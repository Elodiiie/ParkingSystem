package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.annotation.SystemLogAspect;
import com.example.demo.entity.ParkRecord;
import com.example.demo.dto.ParkrecordBycarAndMonth;
import com.example.demo.vo.ResultResponse;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.ParkRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;
import com.zhenzi.sms.ZhenziSmsClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.utils.Constants.MESSAGE_OK;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 14:36
 */
@Api(value = "停车记录方法")
@RestController
@RequestMapping("/parkRecord")
public class ParkRecordHandle {
    @Autowired
    private ParkRecordRepository parkRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private PointsHandle pointsHandle;

    private final static String WRONG_LICENSE = "WRONG LICENSE or UNREGISTERED";
    private final static String LACK_OF_BALANCE = "Lack of balance";
    ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "110280", "90e77681-d2e7-4b9a-8f81-f90694ff2c5e");
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    @ApiOperation(value = "获取停车记录信息")
    @GetMapping("/find")
    public ResultResponse findTOPrint(){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.find());
    }
    @ApiOperation(value = "获取停车记录信息")
    @GetMapping("/findAll/{page}/{size}")
    public ResultResponse findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.findAll1(pageable));
    }
    /***
     * 添加停车记录
     * @param parkRecord
     * @return
     */
    @SystemLog("添加停车记录")
    @ApiOperation(value = "添加停车记录")
    @PostMapping("/addRecord")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public ResultResponse save(@RequestBody ParkRecord parkRecord){
        try {
            Integer userid=carRepository.getUserid(parkRecord.getCarid());
            if(userid ==null){
                return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
            }
            BigDecimal old_balance=userRepository.getBalance(userid);
            System.out.println(old_balance);
            BigDecimal new_balance = old_balance.subtract(parkRecord.getFare());
            System.out.println(old_balance);
            BigDecimal zero=new BigDecimal(0);
            if(new_balance.compareTo(zero)>-1){
                ParkRecord result = parkRecordRepository.save(parkRecord);
                Integer res = userRepository.updateBalance(userid,new_balance);
                pointsHandle.addPoints(userid,parkRecord.getFare().intValue());
                if(result != null&&res!=0){
                    return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
                }else{
                    return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
                }
            }else{
                return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
            }
        }catch (Exception e){
            System.out.println("异常=====" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
        }
    }

    @GetMapping("/findById/{id}")
    public ResultResponse findById(@PathVariable("id") Integer id){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.findById(id).get());
    }

    @ApiOperation(value="根据id查找",notes ="<多表>")
    @GetMapping("/findDetailByParkRecordId/{id}")
    public ResultResponse findDetailByParkRecordId(@PathVariable("id") Integer id){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.findDetailByParkrecordid(id));
    }

    @ApiOperation(value="根据车牌号查找",notes ="<多表>")
    @GetMapping("/findDetailByCarId/{carid}")
    public ResultResponse findDetailByCarId(@PathVariable("carid") Integer carid){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.findDetailByCarid(carid));
    }

    @ApiOperation(value="根据车牌号和月份查找",notes ="微信小程序")
    @GetMapping("/findDetailByCarIdAndMonth/{carId}/{date}")
    public ResultResponse findDetailByCarIdAndMonth(@PathVariable("carId") Integer carid,@PathVariable("date") String date){
        String startTime = date+"-01";
        String endTime = getEndTime(date);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.findDetailByCaridAndMonth(carid,startTime,endTime));
    }

    @ApiOperation(value="用户查询车辆某月份停车记录")
    @PostMapping("/findDetailCarLicenseAndMonth")
    public ResultResponse findDetailCarLicenseAndMonth(@RequestBody ParkrecordBycarAndMonth parkrecordBycarlicenseAndMonth){
        Pageable pageable= PageRequest.of(parkrecordBycarlicenseAndMonth.getPage(),6);
        String startTime = parkrecordBycarlicenseAndMonth.getMonth()+"-01";
        String endTime = getEndTime(parkrecordBycarlicenseAndMonth.getMonth());
        Integer carId =  carRepository.getCarid(parkrecordBycarlicenseAndMonth.getCarlicense());
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.findDetailByCarAndMonth(carId,startTime,endTime,pageable));
    }

    @ApiOperation(value="根据用户号查找",notes ="<多表>")
    @GetMapping("/findDetailByUserId/{userId}")
    public ResultResponse findDetailByUserId(@PathVariable("userId") Integer userId){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.findDetailByUserid(userId));
    }

    @ApiOperation(value="根据用户号和月份查找",notes ="微信小程序")
    @GetMapping("/findDetailByUserIdAndMonth/{userId}/{date}")
    public ResultResponse findDetailByUserIdAndMonth(@PathVariable("userId") Integer carid,@PathVariable("date") String date){
        String starttime = date+"-01";
        String endtime = getEndTime(date);
        System.out.println(starttime+endtime);
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.findDetailByUseridAndMonth(carid,starttime,endtime));
    }

    private String getEndTime(@PathVariable("date") String date) {
        String year = date.substring(0, 4);
        String endtime = "";
        int month = Integer.parseInt(date.substring(5)) + 1;
        if (month == 12) {
            int year_int = Integer.parseInt(year) + 1;
            endtime = year_int + "-01-01";
        } else {
            if(month<10){
                endtime = year + "-0"+month + "-01";
            }else{
                endtime = year + "-"+month + "-01";
            }
        }
        return endtime;
    }

    @ApiOperation(value="根据车牌查找",notes ="<多表>")
    @GetMapping("/findDetailByCarLicense/{license}")
    public ResultResponse findDetailByCarLicense(@PathVariable("license") String license){
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,parkRecordRepository.findDetailByCarlicense(license));
    }

    @SystemLog("修改停车记录")
    @ApiOperation(value = "修改停车记录")
    @PostMapping("/updateParkRecord")
    public ResultResponse update(@RequestBody ParkRecord parkRecord) throws Exception {
        Integer userid=carRepository.getUserid(parkRecord.getCarid());
        if(userid == null){
            return new ResultResponse(Constants.STATUS_FAIL,WRONG_LICENSE,Constants.MESSAGE_FAIL);
        }
        BigDecimal old_balance=userRepository.getBalance(userid);
        BigDecimal minux = parkRecord.getFare().subtract(parkRecordRepository.getFareByParkrecordid(parkRecord.getParkrecordid()));
        BigDecimal new_balance = old_balance.subtract(minux);
        BigDecimal zero=new BigDecimal(0);
        if(new_balance.compareTo(zero)>-1){
            ParkRecord result = parkRecordRepository.save(parkRecord);
            Integer res = userRepository.updateBalance(userid,new_balance);
            pointsHandle.addPoints(userid,minux.intValue());
            if(result != null&&res!=0){
                return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Constants.MESSAGE_OK);
            }else{
                return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Constants.MESSAGE_FAIL);
            }
        }else{
            return new ResultResponse(Constants.STATUS_FAIL,LACK_OF_BALANCE,LACK_OF_BALANCE);
        }
    }

    @PostMapping("sendMess")
    public ResultResponse sendMess(@RequestBody ParkRecord parkRecord) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", "17521344145");//TODO 更换为用户的手机号
        if(parkRecord.getParkrecordid()!=null){
            params.put("templateId", "7174");
            System.out.println("7174");
        }else{
            params.put("templateId", "7166");
            System.out.println("7166");
        }
        String[] templateParams = new String[6];
        templateParams[0] = carRepository.getLicense(parkRecord.getCarid());
        templateParams[1] = String.valueOf(parkRecord.getEntrancetime().toLocaleString()).substring(5,9);
        templateParams[2] = String.valueOf(parkRecord.getEntrancetime().toLocaleString()).substring(10);
        templateParams[3] = String.valueOf(parkRecord.getExittime().toLocaleString()).substring(5,9);
        templateParams[4] = String.valueOf(parkRecord.getExittime().toLocaleString()).substring(10);
        templateParams[5] = String.valueOf(parkRecord.getFare().toString());
        params.put("templateParams", templateParams);
        String result_email = client.send(params);
        logger.info("result_email"+result_email);
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,Boolean.TRUE);
    }
    /**
     * 根据记录id删除车辆信息
     * @param id
     */
    @SystemLog("删除停车记录")
    @DeleteMapping("/deleteById/{id}")
    public ResultResponse deleteById(@PathVariable("id") Integer id){
        Optional<ParkRecord> optional = parkRecordRepository.findById(id);
        if(optional!=null&&optional.isPresent()){
            Integer userid=carRepository.getUserid(optional.get().getCarid());
            parkRecordRepository.deleteById(id);
            pointsHandle.minusPoints(userid,optional.get().getFare().intValue());
        }
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,Boolean.TRUE);
    }
    /***
     * 获取停车记录数量
     * @return
     */
    @ApiOperation(value = "获取停车记录数量")
    @GetMapping("/getCount")
    public ResultResponse getCount(){
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,parkRecordRepository.getCount());
    }

    @GetMapping("/getEntranceCount")
    public ResultResponse getEntranceCount(){
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,parkRecordRepository.getEntranceCount());
    }

    @GetMapping("/getExitCount")
    public ResultResponse getExitCount() {
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,parkRecordRepository.getExitCount());
    }

    @ApiOperation(value="根据用户id查找总计消费")
    @GetMapping("/getNumByUserId/{id}")
    public ResultResponse getNumByUserId(@PathVariable("id") Integer id){
        return new ResultResponse(Constants.STATUS_OK, MESSAGE_OK,parkRecordRepository.getFareByUserid(id));
    }

}
