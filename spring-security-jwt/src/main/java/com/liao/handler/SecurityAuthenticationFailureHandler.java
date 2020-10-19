package com.liao.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final String forwardUrl;

    /**
     * @param forwardUrl
     */
    public SecurityAuthenticationFailureHandler(String forwardUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(forwardUrl),
                () -> "'" + forwardUrl + "' is not a valid forward URL");
        this.forwardUrl = forwardUrl;
    }
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.sendRedirect(forwardUrl);
    }
}
