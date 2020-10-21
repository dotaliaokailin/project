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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/oss")
@Api(value = "OSS对象存储模块", tags = "OSS对象存储接口")
public class OssController {
    @Autowired
    private AliossService aliossService;

    @PostMapping("/uploadImgFile")
    @ApiOperation(value = "上传图片接口", notes = "上传图片，并返回文件地址")
    public Result uploadImgFile(MultipartFile file){
        String url = aliossService.upload(file);
        if(!StringUtils.isEmpty(url))
            return Result.ok().data("url", url);
        else
            throw new BusinessException(ResultCodeEnum.UPLOAD_FAIL.getCode(), ResultCodeEnum.UPLOAD_FAIL.getMessage());
    }

    @PostMapping("/deleteImgFile")
    @ApiOperation(value = "删除图片接口", notes = "删除图片，根据url删除")
    public Result deleteImgFile(@RequestParam("imgUrl") String imgUrl){
        try{
            Boolean flag = aliossService.deleteFile(StringUtils.split(imgUrl, ".com/")[1]);
            if(flag)
                return Result.ok().code(ResultCodeEnum.DELETE_SUCCESS.getCode()).message(ResultCodeEnum.DELETE_SUCCESS.getMessage());
            else
                return Result.error().code(ResultCodeEnum.DELETE_FAIL.getCode()).message(ResultCodeEnum.DELETE_FAIL.getMessage());
        }catch (Exception e){
            throw new BusinessException(ResultCodeEnum.DELETE_FAIL.getCode(), ResultCodeEnum.DELETE_FAIL.getMessage());
        }
    }
}
