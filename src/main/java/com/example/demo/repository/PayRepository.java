package com.example.demo.repository;

import com.example.demo.entity.FareCount;
import com.example.demo.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Elodie
 * @Date: 2021/10/20 21:19
 */
public interface PayRepository extends JpaRepository<Pay,Integer> {
    // 七天内财务用户缴费扣费次数统计
    @Query(value = "SELECT date_format(time,'%Y-%m') months,sum(fare) farecount" +
            " FROM (SELECT * FROM pay WHERE DATE_SUB( CURDATE( ), INTERVAL 6 MONTH ) <= date( time) )as t " +
            "GROUP BY months" ,nativeQuery = true)
    List<FareCount> getFareCount();
    // 根据payid获取fare
    @Query(value = "select fare from pay where payid=?1",nativeQuery = true)
    Double getFareByPayid(int payid);
}