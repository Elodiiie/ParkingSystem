package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Elodie
 * @Date: 2021/9/17 21:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAndPasswd {
    private String username;
    //@JsonIgnore// 序列化成json的时候，该属性不能序列化
    private String password;
}
