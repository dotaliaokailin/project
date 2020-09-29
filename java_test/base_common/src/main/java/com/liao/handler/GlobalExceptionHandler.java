package com.liao.handler;

import com.liao.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error().message(e.getMessage()).data("exception",e.getStackTrace()[0]);
    }

    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.error().message(e.getMessage()).data("exception",e.getStackTrace()[0]);
    }
}
