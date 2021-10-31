package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * @Author: Elodie
 * @Date: 2021/6/20 22:21
 */

@SpringBootTest
public class test {
    @Test
    public static void main(String[] args){
//        try{
//            System.out.println("start");
//            Process pr = Runtime.getRuntime().exec("cmd /k start D:\\Anaconda\\anaconda3\\envs\\hyperLPR\\python.exe " +
//                    "D:\\hyperlpr\\HyperLPR-Meow-master\\lpr_video.py "+"C:\\Users\\98379\\Desktop\\202108021555.mp4");
//            BufferedReader in = new BufferedReader(new
//                    InputStreamReader(pr.getInputStream()));
//            String line;
//            System.out.println(pr.getInputStream());
//            while ((line = in.readLine()) != null) {
//                System.out.println(3);
//            }
//            System.out.println(4);
//            in.close();
//            pr.waitFor();
//            System.out.println("end");
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        int i;
        for(i =0;i<11;++i){
            System.out.println(i);
        }
        System.out.println(i);
    }
}
