package com.example.demo.repository;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Elodie
 * @Date: 2021/11/22 20:47
 */
@Data
@SpringBootTest
class PointsRepositoryTest {
    @Autowired
    private PointsRepository pointsRepository;
    @Test
    public void findByUserid(){
        System.out.println(pointsRepository.findByUserid(2));
    }
}