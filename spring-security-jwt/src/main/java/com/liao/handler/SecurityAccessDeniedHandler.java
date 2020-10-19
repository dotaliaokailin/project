package com.liao.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println( accessDeniedException.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);//403
        response.setHeader("Content-Type","application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write("{\"status\":\"" + HttpServletResponse.SC_FORBIDDEN + "\", \"message\":\"权限不足，请联系管理员！\"}");
        printWriter.flush();
        printWriter.close();
    }
}
