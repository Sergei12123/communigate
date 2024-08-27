package com.example.communigate.controller;

import com.example.communigate.service.UserCache;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@AllArgsConstructor
public class MyControllerAdvice {

    private final UserCache userCache;

    @ModelAttribute
    public void addDefaultAttributes(Model model) {
        model.addAttribute("currentUser", userCache.getCurrentUserName());
    }
}
