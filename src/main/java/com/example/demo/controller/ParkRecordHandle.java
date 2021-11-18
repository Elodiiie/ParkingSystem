package com.example.demo.controller;

import com.example.demo.annotation.SystemLog;
import com.example.demo.vo.EntranceCount;
import com.example.demo.vo.ExitCount;
import com.example.demo.entity.ParkRecord;
import com.example.demo.vo.ParkRecordDetail;
import com.example.demo.vo.ResultResponse;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.ParkRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;
import com.zhenzi.sms.ZhenziSmsClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 14:36
 */
@Api(value = "停车记录方法")
@RestController
@RequestMapping("/parkrecord")
public class ParkRecordHandle {
    @Autowired
    private ParkRecordRepository parkRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;
    //短信
    ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "110280", "90e77681-d2e7-4b9a-8f81-f90694ff2c5e");
    @ApiOperation(value = "获取停车记录信息")
    @GetMapping("/findAll/{page}/{size}")
    public Page<ParkRecord> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return parkRecordRepository.findAll(pageable);
    }
    @ApiOperation(value = "获取停车记录信息1",notes = "多表查询，返回ParkRecordDrtail")
    @GetMapping("/findAll1/{page}/{size}")
    public Page<ParkRecordDetail> findAll1(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable1= PageRequest.of(page,size);
        return parkRecordRepository.findAll1(pageable1);
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
    public Boolean save(@RequestBody ParkRecord parkRecord){
        try {
            Integer userid=carRepository.getUserid(parkRecord.getCarid());
            if(userid ==null){
                return false;
            }
            BigDecimal old_balance=userRepository.getBalance(userid);
            System.out.println(old_balance);
            BigDecimal new_balance = old_balance.subtract(parkRecord.getFare());
            System.out.println(old_balance);
            BigDecimal zero=new BigDecimal(0);
            if(new_balance.compareTo(zero)>-1){
                ParkRecord result = parkRecordRepository.save(parkRecord);
                Integer res = userRepository.updateBalance(userid,new_balance);
                if(result != null&&res!=0){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println("异常=====" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
    @GetMapping("/findById/{id}")
    public ParkRecord findById(@PathVariable("id") Integer id){
        return parkRecordRepository.findById(id).get();
    }
    @ApiOperation(value="根据id查找",notes ="<多表>")
    @GetMapping("/findDetailByParkrecordid/{id}")
    public ParkRecordDetail findDetailByParkrecordid(@PathVariable("id") Integer id){ return parkRecordRepository.findDetailByParkrecordid(id); }
    @ApiOperation(value="根据车牌号查找",notes ="<多表>")
    @GetMapping("/findDetailByCarid/{carid}")
    public List<ParkRecordDetail> findDetailByCarid(@PathVariable("carid") Integer carid){ return parkRecordRepository.findDetailByCarid(carid); }
    @ApiOperation(value="根据车牌号和月份查找",notes ="微信小程序")
    @GetMapping("/findDetailByCaridAndMonth/{carid}/{date}")
    public List<ParkRecordDetail> findDetailByCaridAndMonth(@PathVariable("carid") Integer carid,@PathVariable("date") String date){
        String starttime = date+"-01";
        String endtime = getEndTime(date);
        System.out.println(starttime+endtime);
        return parkRecordRepository.findDetailByCaridAndMonth(carid,starttime,endtime);
    }
    @ApiOperation(value="根据用户号查找",notes ="<多表>")
    @GetMapping("/findDetailByUserid/{userid}")
    public List<ParkRecordDetail> findDetailByUserid(@PathVariable("userid") Integer userid){ return parkRecordRepository.findDetailByUserid(userid); }
    @ApiOperation(value="根据用户号和月份查找",notes ="微信小程序")
    @GetMapping("/findDetailByUseridAndMonth/{userid}/{date}")
    public List<ParkRecordDetail> findDetailByUseridAndMonth(@PathVariable("userid") Integer carid,@PathVariable("date") String date){
        String starttime = date+"-01";
        String endtime = getEndTime(date);
        System.out.println(starttime+endtime);
        return parkRecordRepository.findDetailByUseridAndMonth(carid,starttime,endtime);
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
    @GetMapping("/findDetailByCarlicense/{carlicense}")
    public List<ParkRecordDetail> findDetailByCarlicense(@PathVariable("carlicense") String carlicense){ return parkRecordRepository.findDetailByCarlicense(carlicense); }
    @ApiOperation(value="根据车牌查找分页数据",notes ="<多表>")
    @GetMapping("/findDetailByCarlicense_Page/{carlicense}/{page}/{size}")
    public Page<ParkRecordDetail> findDetailByCarlicense_Page(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("carlicense") String carlicense){
        Pageable pageable1= PageRequest.of(page,size);
        return parkRecordRepository.findDetailByCarlicense_Page(pageable1,carlicense);
    }
    @SystemLog("修改停车记录")
    @ApiOperation(value = "修改停车记录")
    @PutMapping("/updateParkRecord")
    public ResultResponse update(@RequestBody ParkRecord parkRecord) throws Exception {
        ResultResponse resultResponse = new ResultResponse();
        Integer userid=carRepository.getUserid(parkRecord.getCarid());
        if(userid == null){
            resultResponse.setMessage("车牌号不存在");
            resultResponse.setData("fail");
            resultResponse.setCode(Constants.STATUS_FAIL);
        }
        BigDecimal old_balance=userRepository.getBalance(userid);
        BigDecimal minux = parkRecord.getFare().subtract(parkRecordRepository.getFareByParkrecordid(parkRecord.getParkrecordid()));
        BigDecimal new_balance = old_balance.subtract(minux);
        System.out.println("------------------------------------------------------------");
        System.out.println("userid"+userid);
        System.out.println("old_balance"+old_balance);
        System.out.println("new_balance"+new_balance);
        BigDecimal zero=new BigDecimal(0);
        if(new_balance.compareTo(zero)>-1){
            ParkRecord result = parkRecordRepository.save(parkRecord);
            Integer res = userRepository.updateBalance(userid,new_balance);
            if(result != null&&res!=0){
                resultResponse.setMessage("success");
                resultResponse.setData("success");
                resultResponse.setCode(Constants.STATUS_OK);
            }else{
                resultResponse.setMessage("fail");
                resultResponse.setData("fail");
                resultResponse.setCode(Constants.STATUS_FAIL);
            }
        }else{
            resultResponse.setMessage("Lack of balance");
            resultResponse.setData("Lack of balance");
            resultResponse.setCode(Constants.STATUS_FAIL);
        }
        return resultResponse;
    }
    @Async
    @PostMapping("sendMess")
    public void sendMess(@RequestBody ParkRecord parkRecord) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", "17521344145");
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
        System.out.println("result_email"+result_email);
    }
    /**
     * 根据记录id删除车辆信息
     * @param id
     */
    @SystemLog("删除停车记录")
    @DeleteMapping("/deleteById/{id}")
    public void deletecarById(@PathVariable("id") Integer id){
        parkRecordRepository.deleteById(id);
    }
    /***
     * 获取停车记录数量
     * @return
     */
    @ApiOperation(value = "获取停车记录数量")
    @GetMapping("/getCount")
    public Integer getCount(){ return parkRecordRepository.getCount(); }

    @GetMapping("/getEntrancrCount")
    public List<EntranceCount> getEntrancrCount(){ return parkRecordRepository.getEntranceCount(); }

    @GetMapping("/getExitCount")
    public List<ExitCount> getExitCount() { return parkRecordRepository.getExitCount(); }

    @ApiOperation(value="根据用户id查找总计消费")
    @GetMapping("/getNumByUserid/{id}")
    public Double getNumByUserid(@PathVariable("id") Integer id){
        return parkRecordRepository.getFareByUserid(id);
    }
}
