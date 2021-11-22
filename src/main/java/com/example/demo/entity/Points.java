package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: Elodie
 * @Date: 2021/11/22 20:32
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "积分")
public class Points {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pointsid;
    private Integer userid;
    private Integer points;
}
