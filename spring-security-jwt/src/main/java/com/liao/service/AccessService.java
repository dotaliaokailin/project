package com.liao.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AccessService {

    Boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
