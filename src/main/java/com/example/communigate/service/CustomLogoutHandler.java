package com.example.communigate.service;

import com.example.communigate.manager.XimssService;
import com.example.communigate.ximss.request.Bye;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final UserCache userCache;

    private final XimssService ximssService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ximssService.sendRequestToGetNothing(Bye.builder().build());
        userCache.delete(authentication.getName());
    }
}
