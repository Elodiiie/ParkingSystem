package com.example.demo.repository;

import com.example.demo.entity.Points;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Elodie
 * @Date: 2021/11/22 20:40
 */
public interface PointsRepository extends JpaRepository<Points,Integer> {
}
