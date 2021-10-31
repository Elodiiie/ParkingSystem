package com.example.demo.entity;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Elodie
 * @Date: 2021/10/15 17:09
 */

public interface CarDetail {
    Integer getCarid();
    String getUsername();
    String getCarlicense();
    Integer getVip();

}
