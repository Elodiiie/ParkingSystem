package com.example.demo.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * @Author: Elodie
 * @Date: 2021/9/12 20:22
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {
    private int code=20000;
    private String status="success";
    private Object data;

    @Data
    @AllArgsConstructor
    public class Token{
        private String token;
    }
}
