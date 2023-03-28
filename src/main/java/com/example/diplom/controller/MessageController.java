package com.example.diplom.controller;

import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.Signup;
import com.example.diplom.ximss.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@AllArgsConstructor
public class MessageController {

    private final XimssService ximssService;

    @GetMapping("/login")
    private void hello() {
        String url = "http://localhost:8100/ximsslogin/?userName=postmaster&password=qwerty$4";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/signup")
    private void signup() {
        final Response response = ximssService.sendRequest(Signup.builder().userName("user1").password("qwerty$4").build());

    }
}
