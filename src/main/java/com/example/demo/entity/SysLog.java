package com.example.demo.entity;

import lombok.Data;

/**
 * @Author: Elodie
 * @Date: 2021/11/7 18:04
 */

@Data
public class SysLog {
    private String className;

    private String methodName;

    private String params;

    private Long exeuTime;

    private String remark;

    private String createDate;
}
