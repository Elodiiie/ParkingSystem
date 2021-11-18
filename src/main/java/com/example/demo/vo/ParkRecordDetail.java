package com.example.demo.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/10/15 18:23
 */
public interface ParkRecordDetail {
    Integer getParkrecordid();
    String getUsername();
    String getCarlicense();
    Date getEntrancetime();
    Date getExittime();
    BigDecimal getFare();
}
