package com.example.demo.repository;

import com.example.demo.entity.EntranceCount;
import com.example.demo.entity.ParkRecordDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 14:25
 */
@SpringBootTest
class ParkRecordRepositoryTest {
    @Autowired
    private ParkRecordRepository parkRecordRepository;
    @Autowired
    private CarRepository carRepository;
    @Test
    public void findAll(){
        System.out.println(parkRecordRepository.findAll());
    }
    @Test
    public void getBalance(){
        System.out.println(parkRecordRepository.getBalance(19));
    }
    @Test
    public void  getFareByParkrecordid(){
        System.out.println(parkRecordRepository.getFareByParkrecordid(19));
    }
    @Test
    public void findByCarLicense(){
        int carid;
        if(carRepository.getCarid("闽AA975G")==null){

        }else{
            carid=carRepository.getCarid("闽AA975G");
            System.out.println(carid);
            System.out.println(parkRecordRepository.findByCaridOrderByExittimeDesc(carid));
        }

    }
    @Test
    public void getTimeDifferernce(){//小时差
        System.out.println(parkRecordRepository.getTimeDifferernce(5));
    }
    @Test
    public void getDayDifference(){
        System.out.println(parkRecordRepository.getDayDifference(5));
    }
    @Test
    public void getCount(){System.out.println(parkRecordRepository.getCount());}
    @Test
    public void getEntranceCount(){
        List<EntranceCount> list = parkRecordRepository.getEntranceCount();
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).getMonths()+"--");
            System.out.println(list.get(i).getEntrancecount());
        }
        System.out.println(parkRecordRepository.getEntranceCount());
    }
    @Test
    public void  findAll1(){
        Pageable pageable1= PageRequest.of(0,8);
//        System.out.println(parkRecordRepository.findAll1(pageable1));
        System.out.println(parkRecordRepository.findAll(pageable1));
    }
    // 杂七杂八的测试，注释先乱写吧
    @Test
    public List<Integer> currentMonth6(){
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("MM");
//        Date date = new Date();
//        String dateString = sdf.format(cal.getTime());
        List<Integer> rqList = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            dateString = sdf.format(cal.getTime());
//            int month = Integer.parseInt(dateString.substring(0,2));
//            rqList.add(month);
//            cal.add(Calendar.MONTH, -1);
//        }
//        Collections.reverse(rqList);
        return rqList;
    }
    @Test
    public void findEntranceCount(){
//        List<Integer> monthLists = currentMonth6();
//        List<EntranceCount> list = parkRecordRepository.getEntranceCount();
//        List<Integer> countlist = new ArrayList<>();
//        int list_index=0;
//        for(int i =0;i<6;i++){
//            if(monthLists.get(i)==list.get(list_index).getMonths()){
//                countlist.add(list.get(list_index).getEntrancecount());
//                list_index++;
//            }else{
//                countlist.add(0);
//            }
//        }
//        System.out.println(countlist);

    }
}