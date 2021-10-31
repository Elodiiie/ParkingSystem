package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 13:43
 */
@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carid;
    private Integer userid;
    private String Carlicense;
    private Integer vip;

}
