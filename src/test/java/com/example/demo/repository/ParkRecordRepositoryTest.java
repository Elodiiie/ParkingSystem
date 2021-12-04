package com.example.demo.repository;

import com.example.demo.vo.EntranceCount;
import com.example.demo.vo.ParkRecordDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
import java.util.*;

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
    public void findAll() {
        System.out.println(parkRecordRepository.findAll());
    }

    @Test
    public void getBalance() {
        System.out.println(parkRecordRepository.getBalance(19));
    }

    @Test
    public void getFareByParkrecordid() {
        System.out.println(parkRecordRepository.getFareByParkrecordid(19));
    }

    @Test
    public void findByCarLicense() {
        int carid;
        if (carRepository.getCarid("闽AA975G") == null) {

        } else {
            carid = carRepository.getCarid("闽AA975G");
            System.out.println(carid);
            System.out.println(parkRecordRepository.findByCaridOrderByExittimeDesc(carid));
        }

    }

    @Test
    public void getTimeDifferernce() {//小时差
        System.out.println(parkRecordRepository.getTimeDifferernce(5));
    }

    @Test
    public void getDayDifference() {
        System.out.println(parkRecordRepository.getDayDifference(5));
    }

    @Test
    public void getCount() {
        System.out.println(parkRecordRepository.getCount());
    }

    @Test
    public void getEntranceCount() {
        List<EntranceCount> list = parkRecordRepository.getEntranceCount();
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).getMonths() + "--");
            System.out.println(list.get(i).getEntrancecount());
        }
        System.out.println(parkRecordRepository.getEntranceCount());
    }

    @Test
    public void findAll1() {
        Pageable pageable1 = PageRequest.of(0, 8);
//        System.out.println(parkRecordRepository.findAll1(pageable1));
        System.out.println(parkRecordRepository.findAll(pageable1));
    }

    // 杂七杂八的测试，注释先乱写吧
    @Test
    public List<String> currentMonth6() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String dateString = sdf.format(cal.getTime());
        List<String> rqList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            dateString = sdf.format(cal.getTime());
            String month = dateString.substring(0, 7);
            rqList.add(month);
            cal.add(Calendar.MONTH, -1);
        }
        Collections.reverse(rqList);
        return rqList;
    }

    @Test
    public void findEntranceCount() {
        List<String> monthLists = currentMonth6();
        List<EntranceCount> list = parkRecordRepository.getEntranceCount();
        List<Integer> countlist = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            boolean flag =false;//未找到当月数据
            for (int j = 0; j < 6; j++) {
                if (list.get(j).getMonths().equals(monthLists.get(i))) {
                    flag=true;
                    countlist.add(list.get(j).getEntrancecount());
                    break;
                }
            }
            if(!flag){
                countlist.add(0);
            }

        }
        System.out.println(countlist);
    }

    @Test
    public void findDetailByCaridAndMonth() {
        List<ParkRecordDetail> list = parkRecordRepository.findDetailByCaridAndMonth(5, "2021-08-01", "2021-12-01");
        for (ParkRecordDetail li : list) {
            System.out.println(li.getExittime());
        }
    }
}