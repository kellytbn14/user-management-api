package com.example.usermanagementapi.security.jwt;

import com.example.usermanagementapi.utils.MessageResponse;
import com.example.usermanagementapi.utils.StandardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        StandardResponse<String> standardResponse = new StandardResponse<>(
                MessageResponse.AUTHENTICATION_ERROR.getMessage(),
                MessageResponse.AUTHENTICATION_ERROR.getDescription());
        response.getWriter().write(new ObjectMapper().writeValueAsString(standardResponse));
    }
}