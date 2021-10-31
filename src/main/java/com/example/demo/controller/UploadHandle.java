package com.example.demo.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: Elodie
 * @Date: 2021/10/20 16:08
 */
@ApiOperation(value = "上传信息处理")
@RestController
@RequestMapping("/upload")
public class UploadHandle {
    @PostMapping(value = "/avatar")
    public String upload(@RequestParam("avatar") MultipartFile multipartFile) throws IOException {
//        String fileUrl = fastDFSClientWrapper.uploadFile(multipartFile);

        return "https://tse1-mm.cn.bing.net/th/id/R-C.8c372fd892b3bd371eb3a1df8bd7fc88?rik=4KxekfOQD28FKA&riu=http%3a%2f%2fwww.desktx.com%2fd%2ffile%2fwallpaper%2fscenery%2f20170303%2fdfe53a7300794009a029131a062836d5.jpg&ehk=6ayU5y%2fwtGnzhu7g%2bJimm2REgEbHGczl9Mkbg3I1%2b5I%3d&risl=&pid=ImgRaw&r=0";
    }
}
