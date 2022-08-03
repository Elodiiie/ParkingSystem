package com.example.demo.repository;

import com.example.demo.entity.Car;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 13:53
 */
@Data
@SpringBootTest
class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;
    @Test
    void findByCarlicense(){
        Integer carid = carRepository.getCarid("粤AWO54");
        System.out.println(carid);
    }
    @Test
    void getUserId(){
        System.out.println(carRepository.getUserid(3));
    }
    @Test
    void findAll(){
        // 查询所有数据
//        System.out.println(carRepository.findAll());
        System.out.println(carRepository.findDetailByCarid(1).getUsername());
    }
    @Test
    void save(){
        Car car = new Car();
        car.setUserid(1);
        car.setCarlicense("粤C972KL");
        car.setVip(0);
        Car car1 = carRepository.save(car);
        System.out.println(car1);
    }
    @Test
    void findById(){
        Car car = carRepository.findById(1).get();
        System.out.println(car);
    }
    @Test
    void update(){
        Car car = new Car();
        car.setUserid(2);
        car.setCarlicense("粤C536AO");
        car.setVip(0);
        Car car1 = carRepository.save(car);
        System.out.println(car1);
    }
    @Test
    void delete(){
        carRepository.deleteById(7);
    }
    @Test
    void getCount(){System.out.println(carRepository.getCount());}
    @Test
    void findByUserid(){
        System.out.println(carRepository.findByUserid(1));
    }
}
