package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 13:43
 */
@Entity
@Data
@ApiModel(value = "车辆")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carid;
    private Integer userid;
    private String Carlicense;
    private Integer vip;
    private Integer existToUser;//用户删除则改为0

}
