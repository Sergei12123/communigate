package com.example.diplom.controller;

import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.ListFeatures;
import com.example.diplom.ximss.request.Login;
import com.example.diplom.ximss.request.Signup;
import com.example.diplom.ximss.response.Features;
import com.example.diplom.ximss.response.Response;
import com.example.diplom.ximss.response.Session;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MessageController {

    private final XimssService ximssService;

    @GetMapping("/listFeatures")
    private void listFeatures() {
        final Features response = ximssService.sendRequest(ListFeatures.builder().build(), Features.class);

    }

    @GetMapping("/login")
    private void login() {
        final Session session = ximssService.makeDumbLogin(Login.builder().userName("postmaster").password("qwerty$4").build());
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(session.getUrlID(), session, null));
    }

    @GetMapping("/signup")
    private void signup() {
        final Response response = ximssService.sendRequest(Signup.builder().userName("user1").password("qwerty$4").build(), Response.class);

    }
}
