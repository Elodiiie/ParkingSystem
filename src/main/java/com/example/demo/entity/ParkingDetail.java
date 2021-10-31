package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/10/20 22:20
 */

public interface ParkingDetail {
    Integer getParkingid();
    String getUsername();
    String getPhone();
    String getCarlicense();
    Date getEntrancetime();
    void setUsername();
}
