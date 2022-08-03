package com.example.demo.repository;

import com.example.demo.entity.Feedback;
import com.example.demo.vo.CarDetail;
import com.example.demo.vo.FeedbackVo;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Elodie
 * @Date: 2021/11/19 16:59
 */
@Data
@SpringBootTest
class FeedbackRepositoryTest {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private CarRepository carRepository;
    @Test
    void findByIsRead(){
        Page<FeedbackVo> feedbacks = feedbackRepository.findByIsRead(PageRequest.of(0,8),0);
//        System.out.println(feedbacks.getContent().get(0).get);
    }

    @Test
    void find(){
        Page<FeedbackVo> feedbacks = feedbackRepository.findAll1(PageRequest.of(0,8));
    }
}
