package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Elodie
 * @Date: 2022/3/18 15:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkrecordBycarAndMonth {
    private String carlicense;
    private String month;

    private int page;
}
