package com.example.diplom.service;

import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.SetSessionOption;
import com.example.diplom.ximss.request.SignalBind;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RedirectAfterLoginFilter extends OncePerRequestFilter {

    @Setter
    private XimssService ximssService;

    @Value("${server.servlet.session.timeout}")
    private String sessionTimeout;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
            && (request.getRequestURI().equals(request.getContextPath() + "/login")
            || request.getRequestURI().equals(request.getContextPath() + "/"))) {
            ximssService.sendRequestToGetNothing(
                SetSessionOption.builder()
                    .name("idleTimeout")
                    .value(String.valueOf(Integer.parseInt(sessionTimeout.replaceAll("\\D+", "")) * 60))
                    .build());
            ximssService.sendRequestToGetNothing(SignalBind.builder().build());
//            ximssService.sendRequests();
            response.sendRedirect(request.getContextPath() + "/message/all?login");
        } else {
            if (request.getRequestURI().equals("/filter/chat/addMessageToChat")) {
                response.sendRedirect(request.getContextPath() + request.getRequestURI().replace("/filter", "") + "?" + request.getQueryString());
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}