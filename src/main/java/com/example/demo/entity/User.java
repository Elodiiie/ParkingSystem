package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 19:19
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "用户对象")
public class User implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;
//    private BigDecimal balance;
    @ApiModelProperty(notes = "nickname of the user",name = "name",required = true,value = "姓名")
    private String username;
    //@JsonIgnore// 序列化成json的时候，该属性不能序列化
    @ApiModelProperty(notes = "password of the user",name = "password",required = true,value = "密码")
    private String password;
    private Integer sex;
    private String phone;
    private String email;
    private String avatar;
    private String introduction;
    private String roles;
    private BigDecimal balance;
    private Date lastLoginTime;
}
