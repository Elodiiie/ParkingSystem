package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: Elodie
 * @Date: 2022/3/26 21:26
 */
@Entity
@Data
@ApiModel(value = "账户登陆信息")
public class AccountSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accSessId;
    private int userid;
    private String sessionid;
}
