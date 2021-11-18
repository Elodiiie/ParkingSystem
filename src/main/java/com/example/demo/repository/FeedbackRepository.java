package com.example.demo.repository;

import com.example.demo.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Elodie
 * @Date: 2021/11/18 15:22
 */
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
}
