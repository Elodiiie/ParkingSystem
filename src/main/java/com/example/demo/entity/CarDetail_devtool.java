package com.example.demo.entity;

import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/11/13 16:00
 * 微信小程序。用户得到本人车辆车牌，是否在停车场，在的话具体时间，不在的话返回最后离开时间
 */
public interface CarDetail_devtool {
    Integer getCarid();
    String getCarlicense();
    Integer getExist();
    Date getTime();
}
