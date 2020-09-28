package com.liao.handler;

import com.liao.response.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message(e.getMessage()).data("exception",e.getStackTrace()[0]);
    }

    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException e){
        e.printStackTrace();
        return Result.error().message(e.getMessage()).data("exception",e.getStackTrace()[0]);
    }
}
