package com.example.diplom.service;

import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.Bye;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final RedisRepository redisRepository;

    private final XimssService ximssService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        ximssService.sendRequestToGetNothing(Bye.builder().build());
        redisRepository.delete(authentication.getName());
        System.out.println("User logged out successfully!");
        response.sendRedirect("/login?logout");
    }

}
