package com.example.demo.repository;

import com.example.demo.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 14:24
 */

public interface ParkRecordRepository extends JpaRepository<ParkRecord,Integer> {
    @Query(value ="select DATEDIFF(exitTime,entranceTime) from parkrecord where parkrecordid = ?1", nativeQuery = true)
    Integer getDayDifference(int parkrecordid);
    @Query(value="select TIMESTAMPDIFF(hour,entranceTime,exitTime) from parkrecord where parkrecordid = ?1",nativeQuery = true)
    Integer getTimeDifferernce(int parkrecordid);
//    @Query(value = "select * from parkrecord where carid = ?1 order by exittime",nativeQuery = true)
    ParkRecord findByCaridOrderByExittimeDesc(int carid);
    @Query(value="select fare from parkrecord where parkrecordid = ?1",nativeQuery = true)
    BigDecimal getFareByParkrecordid(int parkrecordid);
    @Query(value="select user.balance from parkrecord,car,user where parkrecord.carid=car.carid and car.userid=user.userid and parkrecordid = ?1",nativeQuery = true)
    BigDecimal getBalance(int parkrecordid);
    @Query(value="select user.userid from parkrecord,car,user where parkrecord.carid=car.carid and car.userid=user.userid and parkrecordid = ?1",nativeQuery = true)
    Integer getUserId(int parkrecordid);
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and parkrecordid = ?1",nativeQuery = true)
    ParkRecordDetail findDetailByParkrecordid(int parkrecoedid);
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and parkrecord.carid = ?1",nativeQuery = true)
    List<ParkRecordDetail> findDetailByCarid(int carid);
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and car.carlicense = ?1",nativeQuery = true)
    List<ParkRecordDetail> findDetailByCarlicense(String carlicense);
    // 分页查询 根据Carlicense 返回ParkRecordDetail
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and car.carlicense = ?1",nativeQuery = true)
    Page<ParkRecordDetail> findDetailByCarlicense_Page(Pageable pageable,String carlicense);
    // 分页查询所有 返回ParkRecordDetail
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid " ,nativeQuery = true)
    Page<ParkRecordDetail> findAll1(Pageable pageable1);
    // 统计总停车记录
    @Query(value = "select count(*) from parkrecord" , nativeQuery = true)
    Integer getCount();
    // 七天内进场次数统计
    @Query(value = "SELECT date_format(entrancetime,'%Y-%m') months,count(*) entrancecount" +
            " FROM (SELECT * FROM parkrecord WHERE DATE_SUB( CURDATE( ), INTERVAL 6 MONTH ) <= date( entranceTime) )as t " +
            "GROUP BY months" ,nativeQuery = true)
    List<EntranceCount> getEntranceCount();
    // 七天内离场次数统计
    @Query(value = "SELECT date_format(exittime,'%Y-%m') months,count(*) exitcount" +
            " FROM (SELECT * FROM parkrecord WHERE DATE_SUB( CURDATE( ), INTERVAL 6 MONTH ) <= date( exittime) )as t " +
            "GROUP BY months" ,nativeQuery = true)
    List<ExitCount> getExitCount();
    // 七天内财务停车扣费次数统计
    @Query(value = "SELECT date_format(exittime,'%Y-%m') months,sum(fare) farecount" +
            " FROM (SELECT * FROM parkrecord WHERE DATE_SUB( CURDATE( ), INTERVAL 6 MONTH ) <= date( exittime) )as t " +
            "GROUP BY months" ,nativeQuery = true)
    List<FareCount> getFareCount();
    @Query(value = "select sum(fare) from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and user.userid=?1" ,nativeQuery = true)
    Double getFareByUserid(int userid);
}
