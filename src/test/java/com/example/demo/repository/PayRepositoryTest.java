package com.example.demo.repository;

import com.example.demo.vo.FareCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Elodie
 * @Date: 2021/12/4 19:12
 */
@SpringBootTest
class PayRepositoryTest {
    @Autowired
    private PayRepository payRepository;
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
    public void findFareCount() {
        List<String> monthLists = currentMonth6();
        List<FareCount> list = payRepository.getFareCount();
        List<Integer> countlist = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            boolean flag =false;//未找到当月数据
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getMonths().equals(monthLists.get(i))) {
                    flag=true;
                    countlist.add(list.get(j).getFarecount());
                    break;
                }
            }
            if(!flag){
                countlist.add(0);
            }

        }
        System.out.println(countlist);
    }

}