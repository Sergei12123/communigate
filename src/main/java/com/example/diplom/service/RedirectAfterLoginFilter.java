package com.example.diplom.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RedirectAfterLoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && (request.getRequestURI().equals(request.getContextPath() + "/login")
                || request.getRequestURI().equals(request.getContextPath() + "/"))) {
            response.sendRedirect(request.getContextPath() + "/hello?login");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}