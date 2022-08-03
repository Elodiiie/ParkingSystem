package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author: Elodie
 * @Date: 2021/11/14 17:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Balance {
    private Integer userid;
    //@JsonIgnore// 序列化成json的时候，该属性不能序列化
    private BigDecimal Balance;
}
