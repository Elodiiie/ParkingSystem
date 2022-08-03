package com.example.demo.repository;

import com.example.demo.vo.EntranceCount;
import com.example.demo.vo.ExitCount;
import com.example.demo.vo.FareCount;
import com.example.demo.entity.ParkRecord;
import com.example.demo.vo.ParkRecordDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 14:24
 */

public interface ParkRecordRepository extends JpaRepository<ParkRecord,Integer> , JpaSpecificationExecutor<ParkRecord> {
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid",nativeQuery = true)
    List<ParkRecordDetail> find();
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
    // 再判断月份
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and parkrecord.carid = ?1 and DATE_FORMAT(exittime,'%Y-%m-%d') between ?2 and ?3",nativeQuery = true)
    List<ParkRecordDetail> findDetailByCaridAndMonth(int carid,String starttime,String endtime);
    //分页
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and parkrecord.carid = ?1 and DATE_FORMAT(exittime,'%Y-%m-%d') between ?2 and ?3",nativeQuery = true)
    Page<List<ParkRecordDetail>> findDetailByCarAndMonth(int carid,String starttime,String endtime,Pageable pageable);
    //修改 根据userid得到所有car
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and user.userid = ?1 and car.existToUser = 1 order by exittime desc",nativeQuery = true)
    List<ParkRecordDetail> findDetailByUserid(int userid);
    //再判断月份
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and user.userid = ?1 and car.existToUser = 1 and DATE_FORMAT(exittime,'%Y-%m-%d') between ?2 and ?3 order by exittime desc",nativeQuery = true)
    List<ParkRecordDetail> findDetailByUseridAndMonth(int userid,String starttime,String endtime);
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and car.carlicense = ?1",nativeQuery = true)
    List<ParkRecordDetail> findDetailByCarlicense(String carlicense);
    // 分页查询 根据Carlicense 返回ParkRecordDetail
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid and car.carlicense = ?1",nativeQuery = true)
    Page<ParkRecordDetail> findDetailByCarlicense_Page(Pageable pageable,String carlicense);
    // 分页查询所有 返回ParkRecordDetail
    @Query(value = "select parkrecordid,user.username,car.carlicense,entrancetime,exittime,fare from car,user,parkrecord where parkrecord.carid = car.carid " +
            "and car.userid = user.userid order by parkrecordid" ,nativeQuery = true)
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
            "and car.userid = user.userid and user.userid=?1 and car.existToUser=1" ,nativeQuery = true)
    Double getFareByUserid(int userid);


}
