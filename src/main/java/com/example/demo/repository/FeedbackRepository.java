package com.example.demo.repository;

import com.example.demo.entity.Feedback;
import com.example.demo.vo.CarDetail;
import com.example.demo.vo.FeedbackVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Elodie
 * @Date: 2021/11/18 15:22
 */
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    @Query(value = "select feedbackid,user.username,type,content,isread from feedback,User where feedback.userid=user.userid and isread=?1",nativeQuery = true)
    Page<FeedbackVo> findByIsRead(Pageable pageable,int isread);
    @Query(value = "select feedbackid,username,type,content,isread from Feedback,User where Feedback.userid=User.userid",nativeQuery = true)
    Page<FeedbackVo> findAll1(Pageable pageable);

}
