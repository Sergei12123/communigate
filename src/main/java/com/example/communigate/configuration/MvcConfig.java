package com.example.communigate.configuration;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        Arrays.asList("/", "/login", "/auth", "/auth/login").forEach(key -> registry.addViewController(key).setViewName("login"));
        Arrays.asList("/registration", "/auth/registration").forEach(key -> registry.addViewController(key).setViewName("registration"));

    }

}
