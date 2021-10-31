package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author: Elodie
 * @Date: 2021/6/20 14:21
 */
@Entity
@Data
@NoArgsConstructor
public class ParkRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parkrecordid;

    private Integer carid;
    //对于转换前端传过来的时间，@JsonFormat只适合 Content-Type 为application/json的请求，如果是表单请求，则采用@DateTimeFormat。
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date entrancetime;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date exittime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date exittime;
    private BigDecimal fare;


}
