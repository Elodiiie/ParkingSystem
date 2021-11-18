package com.example.demo.repository;


import com.example.demo.vo.CarDetail_devtool;
import com.example.demo.vo.ParkingDetail;
import com.example.demo.vo.CarDetail_devtool;
import com.example.demo.entity.Parking;
import com.example.demo.vo.ParkingDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Elodie
 * @Date: 2021/8/9 22:13
 */
public interface ParkingRepository extends JpaRepository<Parking,Integer> {
    @Query(value ="select count(*) from Parking where carlicense =?1", nativeQuery = true)
    Integer existsByCarlicense(String carlicense);//1为存在，0为不存在

    Parking findByCarlicense(String carlicense);//符合条件的行数据记录
    @Query(value = "select count(*) from parking" , nativeQuery = true)
    Integer getCount();
    // 分页查询所有 返回ParkingDetail
    @Query(value = "select parkingid,user.username,car.carlicense,entrancetime from car,user,parking where parking.carlicense = car.carlicense " +
            "and car.userid = user.userid order by entrancetime" ,nativeQuery = true)
    Page<ParkingDetail> findAll1(Pageable pageable1);
    @Query(value = "select parkingid,user.username,phone,car.carlicense,entrancetime from car,user,parking where parking.carlicense = car.carlicense " +
            "and car.userid = user.userid and car.carlicense=?1" ,nativeQuery = true)
    ParkingDetail findParkingDetailByCarlicense(String carlicense);
    @Query(value = "select username from user,car where user.userid=car.userid and phone=?1 and carlicense =?2",nativeQuery = true)
    String findUsernameByPhoneAndAndCarlicense(String phone,String carlicense);
    //微信小程序。用户得到本人车辆车牌，是否在停车场，在的话具体时间，不在的话返回最后离开时间
    @Query(value = "select car.carid,car.carlicense,COUNT(parkingid) as exist,if(COUNT(parkingid)=1,parking.entranceTime,(select exittime from parkrecord,car where car.CarId=parkrecord.carid and CarLicense = ?1 ORDER BY exittime desc limit 1)) as time" +
            " from parking,car where car.carlicense=parking.carlicense" +
            " and car.carlicense = ?1",nativeQuery = true)
    CarDetail_devtool getCarDetialBywx_overall (String carlicense);

}
