package com.liao.system.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
@Api(value = "Kaptcha验证码模块", tags = "Kaptcha验证码")
public class KaptchaController {
    /**
     * 验证码工具
     */
    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "获取Kaptcha验证码接口", notes = "获取Kaptcha验证码")
    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        byte[] captcha = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // 将生成的验证码保存在session中
            String createText = defaultKaptcha.createText();
            //request.getSession().setAttribute("rightCode", createText);
            //放redis，key为sessionId,value为验证码，3分钟失效
            redisUtil.set(request.getRemoteAddr(), createText, 180);
            BufferedImage bi = defaultKaptcha.createImage(createText);
            ImageIO.write(bi, "jpg", out);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        captcha = out.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(captcha);
        servletOutputStream.flush();
        servletOutputStream.close();
    }


    @GetMapping("/checkCode")
    @ApiOperation(value = "校验验证码接口", notes = "校验验证码")
    public Result checkCode(@RequestParam("code") String code, HttpServletRequest request){
        Object codeObj = redisUtil.get(request.getRemoteAddr());
        if(null == codeObj){//验证码已失效
            throw new BusinessException(ResultCodeEnum.CODE_EXPIRE.getCode(), ResultCodeEnum.CODE_EXPIRE.getMessage());
        }
        if(!((String) codeObj).equals(code)){//验证码错误
            throw new BusinessException(ResultCodeEnum.CODE_ERROR.getCode(), ResultCodeEnum.CODE_ERROR.getMessage());
        }
        return Result.ok();
    }
}
