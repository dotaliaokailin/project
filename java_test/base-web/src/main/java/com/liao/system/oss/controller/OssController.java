package com.liao.system.oss.controller;

import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.oss.service.AliossService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/0ss")
@Api(value = "OSS对象存储模块", tags = "OSS对象存储接口")
public class OssController {
    @Autowired
    private AliossService aliossService;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件接口", notes = "上传文件，并返回文件地址")
    public Result upload(MultipartFile file){
        String url = aliossService.upload(file);
        if(!StringUtils.isEmpty(url))
            return Result.ok().data("url", url);
        else
            throw new BusinessException(ResultCodeEnum.UPLOAD_FAIL.getCode(), ResultCodeEnum.UPLOAD_FAIL.getMessage());
    }
}
