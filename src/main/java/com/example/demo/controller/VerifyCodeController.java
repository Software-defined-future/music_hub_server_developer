package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.VerificationCodeUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import java.awt.image.BufferedImage;

@RestController
@CrossOrigin
public class VerifyCodeController {
    @ResponseBody
    @GetMapping(value = "/Captcha.jpg")
    public Object getCaptcha(){
        JSONObject jsonObject = new JSONObject();
        try{
            VerificationCodeUtil captcha = new VerificationCodeUtil();
            StringBuffer code = new StringBuffer();
            BufferedImage image = captcha.genRandomCodeImage(code);
            String base64 = captcha.BufferedImageToBase64(image);
            jsonObject.put("image",base64);
            jsonObject.put("code",1);
            jsonObject.put("verifyCode",code.toString());
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("code",0);
            return jsonObject;
        }
    }



}
