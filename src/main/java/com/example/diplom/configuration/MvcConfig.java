package com.example.diplom.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/new-message").setViewName("new-message");
        registry.addViewController("/rules").setViewName("rules");
        registry.addViewController("/new-rule").setViewName("new-rule");
        registry.addViewController("/login").setViewName("login");
    }

}
