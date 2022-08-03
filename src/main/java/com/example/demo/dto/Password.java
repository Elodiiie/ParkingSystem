package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Elodie
 * @Date: 2021/11/14 21:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Password {
    private int userid;
    private String password;
}
