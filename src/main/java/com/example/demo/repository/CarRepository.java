package com.example.demo.repository;

import com.example.demo.entity.Car;
import com.example.demo.entity.CarDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 13:50
 */
public interface CarRepository extends JpaRepository<Car,Integer> {
    @Query("select carid from Car car where car.Carlicense=?1")
    Integer getCarid(String license);
    @Query(value = "select carlicense from car where carid=?1",nativeQuery = true)
    String getLicense(int carid);
    @Query("select userid from Car where carid=?1")
    Integer getUserid(int carid);
    @Query(value = "select carid,username,carlicense,vip from Car,User where user.userid=car.userid and carid=?1",nativeQuery = true)
    CarDetail findDetailByCarid(int carid);
    @Query(value = "select carid,username,carlicense,vip from Car,User where user.userid=car.userid ",nativeQuery = true)
    Page<CarDetail> findAll1(Pageable pageable1);
    @Query(value = "select carid,username,carlicense,vip from Car,User where user.userid=car.userid and carlicense=?1",nativeQuery = true)
    CarDetail findDetailByCarlicense(String carlicense);
    @Query(value = "select count(distinct carlicense) from car" , nativeQuery = true)
    Integer getCount();
}
