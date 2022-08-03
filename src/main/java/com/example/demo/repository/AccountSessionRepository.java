package com.example.demo.repository;

import com.example.demo.entity.AccountSession;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Elodie
 * @Date: 2022/3/26 21:30
 */
public interface AccountSessionRepository extends JpaRepository<AccountSession,Integer> {
    Boolean existsByUseridIs(int userid);
    AccountSession getAccountsessionByUseridIsAndSessionidIs(int userid, String sessionid);
    AccountSession getAccountSessionByUseridIs(int userid);
}
