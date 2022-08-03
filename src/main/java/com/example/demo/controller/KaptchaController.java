package com.example.demo.controller;

import com.example.demo.utils.Constants;
import com.example.demo.vo.ResultResponse;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class KaptchaController {
    private static final Logger logger = LoggerFactory.getLogger(KaptchaController.class);



    /**
     * 1、验证码工具
     */
    @Resource
    DefaultKaptcha defaultKaptcha;

    private final static String WRONG = "The verification code is incorrect";
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }
    /**
     * 2、生成验证码
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @GetMapping("/defaultKaptcha")
    public ResultResponse defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("rightCode", createText);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
            logger.info("获取验证码成功>>>>>> " + createText);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            logger.error("获取验证码失败>>>>>>> ", e);
            return new ResultResponse(Constants.STATUS_FAIL,Constants.MESSAGE_FAIL,Boolean.FALSE);
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
        return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
    }

    /**
     * 3、校对验证码
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @GetMapping("/imgVerifyControllerDefaultKaptcha/{tryCode}")
    @ResponseBody
    public ResultResponse imgVerifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("tryCode") String tryCode) throws IOException {
        String rightCode;
        try{
            rightCode = (String) httpServletRequest.getSession(Boolean.FALSE).getAttribute("rightCode");
        }catch (Exception e){
            logger.error("获取session验证码失败");
            return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
        }
        System.out.println("rightCode"+rightCode);
        System.out.println("tryCode"+tryCode);
        if (!rightCode.equalsIgnoreCase(tryCode)) {
            logger.error("验证码错误=======>>>>>> ");
            return new ResultResponse(Constants.BUSINESS_FAIL,WRONG,Boolean.FALSE);
        } else {
            logger.info("验证码正确=======>>>>>> ");
            return new ResultResponse(Constants.STATUS_OK,Constants.MESSAGE_OK,Boolean.TRUE);
        }
    }

    @GetMapping("/toIndex")
    public String toIndex() {
        return "index";
    }
}
