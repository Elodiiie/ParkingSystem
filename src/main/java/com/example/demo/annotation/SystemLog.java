package com.example.demo.annotation;
import java.lang.annotation.*;
/**
 * @Author: Elodie
 * @Date: 2021/11/7 17:55
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用在参数和方法上
@Retention(RetentionPolicy.RUNTIME)//运行时注解
@Documented//表明这个注解应该被 javadoc工具记录
public @interface SystemLog {
    String value() default "";

}
