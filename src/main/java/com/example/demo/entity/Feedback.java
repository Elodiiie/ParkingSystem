package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: Elodie
 * @Date: 2021/11/18 15:15
 */
@Entity
@Data
@ApiModel(value = "问题反馈")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackid;
    private Integer userid;
    private String content;
    private String imageUrl;
    private Integer isRead;
    private Integer type;
}
