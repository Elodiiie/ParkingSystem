package com.example.demo.repository;

import com.example.demo.entity.Parking;
import com.example.demo.vo.ParkingDetail;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/8/9 22:15
 */
@Data
@SpringBootTest
class ParkingRepositoryTest {//!车牌数据设为unique
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private CarRepository carRepository;
    @Test
    void existParking(){//是否在停车场内
        System.out.println(parkingRepository.existsByCarlicense("粤CLS54Q"));
    }
    @Test
    void findAll(){
        System.out.println(parkingRepository.findAll());
    }
    @Test
    void findById(){
        System.out.println(parkingRepository.findById(1));
    }
    @Test
    void addParkingCar(){//车辆进入停车场
        String carlicense = "粤B78ALW";
        Date entranceTime = new Date(2021-1900,8,10,11,12,0);//在IE浏览器中不支持格式“2017-08-08”，需要转换为“2017/08/08”
        Parking parking =new Parking();
        parking.setCarlicense(carlicense);
        parking.setEntrancetime(entranceTime);
        parkingRepository.save(parking);
    }
    @Test
    void updateParkingCar(){
        int id=3;
        String carlicense = "粤AWO542";
        Date entranceTime = new Date(2021-1900,8,10,17,12,0);//在IE浏览器中不支持格式“2017-08-08”，需要转换为“2017/08/08”
        Parking parking =new Parking();
        parking.setParkingid(3);
        parking.setCarlicense(carlicense);
        parking.setEntrancetime(entranceTime);
        parkingRepository.save(parking);
    }
    @Test
    void findByCarlicense(){//在停车场数据中根据车牌查找是否存在
        System.out.println(parkingRepository.findByCarlicense("粤B78ALW"));
    }
    @Test
    void getEntranceTimeByCarlicense(){
        System.out.println(parkingRepository.findByCarlicense("粤B78ALW").getEntrancetime());
    }
    @Test
    void deleteParkingCar(){//车辆离场，删除停车场内数据
        int id = parkingRepository.findByCarlicense("粤AWO542").getParkingid();
        System.out.println(id);
        parkingRepository.deleteById(id);
    }
    @Test
    void getCount(){
        System.out.println(parkingRepository.getCount());
    }
    @Test
    void findParkingDetailByCarlicense(){
//        ParkingDetail detail = parkingRepository.findParkingDetailByCarlicense("粤CLS54Q");
        ParkingDetail detail = parkingRepository.findParkingDetailByCarlicense("粤B78ALW");
        if(detail!=null){
            System.out.println(detail.getCarlicense());
            System.out.println(detail.getEntrancetime());
            System.out.println(detail.getParkingid());
            System.out.println(detail.getPhone());
            System.out.println(detail.getUsername());
        }else{

            System.out.println(detail);
        }

    }
    @Test
    void findUsernameByPhoneAndAndCarlicense(){
        System.out.println(parkingRepository.findUsernameByPhoneAndAndCarlicense("17521344145","粤B78ALW"));
    }
    @Test
    void getCarDetialBywx_overall(){
        System.out.println(parkingRepository.getCarDetailByWx_in("粤CLS54Q").getTime());
        System.out.println(parkingRepository.getCarDetailByWx_out("粤B78ALW").getTime());
    }
}
