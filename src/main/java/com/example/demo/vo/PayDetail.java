package com.example.demo.vo;

import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/11/18 20:08
 */
public interface PayDetail {
    Integer getpayid();
    String getUsername();
    Date getTime();
    Double getFare();
}
