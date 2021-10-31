package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/10/17 20:06
 */

public interface EntranceCount {
    String getMonths();
    Integer getEntrancecount();
}
