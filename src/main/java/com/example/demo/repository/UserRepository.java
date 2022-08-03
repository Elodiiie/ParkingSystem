package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Elodie
 * @Date: 2021/6/20 19:21
 */

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value ="select balance from User where userid =?1", nativeQuery = true)
    BigDecimal getBalance(int userid);
    @Modifying
    @Transactional
    @Query(value = "update user set balance = ?2 where userid = ?1",nativeQuery = true)
    Integer updateBalance(int userid,BigDecimal balance);
    //验证用户名密码,返回数字
    @Query(value = "select count(*) from User where username=?1 and password=?2",nativeQuery = true)
    public Integer verifyPassword(String userName, String password);
    //验证用户名密码,返回数字
    @Query(value = "select count(*) from User where username=?1 and phone=?2",nativeQuery = true)
    public Integer verifyPhoneAndUsername(String userName, String phone);
    //验证用户名
    @Query(value = "select count(*) from User where username=?1",nativeQuery = true)
    Integer verifyUser(String userName);
    //修改用户登录最后时间
    @Modifying
    @Transactional
    @Query(value = "update User set lastLoginTime = now() where username=?1",nativeQuery = true)
    Integer updateLastLoginTimeByUsername(String username);
    //获取用户信息userinfo
    @Query(value = "select avatar from User where username=?1",nativeQuery = true)
    String findAvatarByname(String username);
    @Query(value = "select introduction from User where username=?1",nativeQuery = true)
    String findIntroductionByname(String username);
    @Query(value = "select roles from User where username=?1",nativeQuery = true)
    String findRolesByname(String username);
    // 通过username获取userid
    @Query(value = "select userid from User where username=?1",nativeQuery = true)
    Integer findUseridByUsername(String username);
    // 手机模糊查找
    List<User> findByPhoneIsLike(String phone);
    // 手机具体查找
    User findByPhone(String phone);
    Boolean existsByPhoneIs(String phone);
    // 统计用户
    @Query(value = "select count(distinct username) from user" , nativeQuery = true)
    Integer getCount();

    User getUserByUsernameIsAndPasswordIs(String username,String password);

    @Modifying
    @Transactional
    @Query(value = "update User set password = ?2 where userid=?1",nativeQuery = true)
    public Integer updatePassword(int userid,String password);
}
