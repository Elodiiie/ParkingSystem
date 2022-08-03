package com.example.demo.repository;

import lombok.Data;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: Elodie
 * @Date: 2022/3/26 21:32
 */
@Data
@SpringBootTest
public class AccountSessionRepositoryTest {
    @Autowired
    private AccountSessionRepository accountSessionRepository;
    @Test
    void isExist(){
        System.out.println(accountSessionRepository.existsByUseridIs(0));
    }
    @Test
    void update() {
        System.out.println(accountSessionRepository.getAccountsessionByUseridIsAndSessionidIs(1,"1"));
        // TODO update
    }
    @Test
    void pass() {
        // TODO delete
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("XyO8frEh0i4dX14S");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("glx000630");
        String passwordDecrypt = textEncryptor.decrypt(password);
        System.out.println("username: "+username);
        System.out.println("password: "+password);
        System.out.println("passwordDecrypt "+passwordDecrypt);

    }
}
