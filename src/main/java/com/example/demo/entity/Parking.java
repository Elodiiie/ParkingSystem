package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/8/9 21:57
 * 停车场内情况表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel(value = "车位信息")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parkingid;
    private String carlicense;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date entrancetime;
}
