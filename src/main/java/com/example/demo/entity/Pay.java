package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/10/20 21:17
 */
@Entity
@Data
@ApiModel(value = "缴费")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer payid;
//    private String username;
    private Integer userid;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;
    private Double fare;
}
