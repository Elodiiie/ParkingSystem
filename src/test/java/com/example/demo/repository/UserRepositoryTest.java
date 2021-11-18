package com.example.demo.repository;

import com.example.demo.entity.User;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 19:22
 */
@Data
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void findAll(){
//        String username="user2";
//        userRepository.findAll();
////        System.out.println(userRepository.verifyPassword("user1","user1"));
////        System.out.println(userRepository.findInfoByname("user1"));
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUname(username);
//        userInfo.setUavatar(userRepository.findAvatarByname(username));
//        userInfo.setUintroduction(userRepository.findIntroductionByname(username));
//        List<String> roles;
//        if(userRepository.findRolesByname(username).equals("admin")){
//            roles = Arrays.asList("admin");
//        }else{
//            roles = Arrays.asList("editor");
//        }
//        userInfo.setRoles(roles);
//        System.out.println(userInfo);
            System.out.println(userRepository.findUseridByUsername("user3"));
    }
    @Test
    public  void updateLastLoginTime(){
        System.out.println(userRepository.updateLastLoginTimeByUsername("user1"));
        System.out.println(userRepository.findById(1));
    }
    @Test
    public void findByPhoneIsLike(){
        System.out.println(userRepository.findByPhoneIsLike("17521"+"%"));
    }
    @Test
    public void findByPhone(){
        System.out.println(userRepository.findByPhone("17521344"));
    }
    @Test
    void UpdateBalance(){
        System.out.println("当前余额"+userRepository.getBalance(4));//返回null错误
        System.out.println(userRepository.updateBalance(21,new BigDecimal(10+100)));//返回0错误
        System.out.println("当前余额"+userRepository.getBalance(1));
    }
    @Test
    void save(){
        User user = new User();
        user.setUsername("user4");
        user.setSex(0);
        user.setPhone("13360772647");
        user.setPassword("user4");
        User user1 = userRepository.save(user);
        System.out.println(user1);
    }
    @Test
    void findById(){
        User car = userRepository.findById(18).get();
        System.out.println(car);
    }
    @Test
    void update(){
        User user = new User();
        user.setUserid(5);
        user.setUsername("user5");
        user.setSex(0);
        user.setPhone("13360772647");
        User user1 = userRepository.save(user);
        System.out.println(user1);
    }
    @Test
    void delete(){
        userRepository.deleteById(7);
    }
    @Test
    void getCount(){System.out.println(userRepository.getCount());}
    @Test
    void updatePasswd(){
        System.out.println(userRepository.updatePassword(0,"useri12"));
    }
}