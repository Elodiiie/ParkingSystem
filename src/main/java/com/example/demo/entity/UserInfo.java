package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfo {
    private Integer userid;
    private String name;
    private String avatar;
    private String introduction;
    private List<String> roles;
}
