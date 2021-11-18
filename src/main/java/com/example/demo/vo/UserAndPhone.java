package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Elodie
 * @Date: 2021/10/24 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAndPhone {
    private String username;
    private String phone;
}
