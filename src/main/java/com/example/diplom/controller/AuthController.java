package com.example.diplom.controller;

import com.example.diplom.dto.UserDTO;
import com.example.diplom.manager.AuthService;
import com.example.diplom.ximss.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private static final String REGISTRATION = "registration";

    private static final String USER_DTO = "userDto";

    private static final String REDIRECT_LOGIN = "redirect:/auth/login";

    private static final String REDIRECT_REGISTRATION = "redirect:/auth/registration";

    @GetMapping("/registration")
    public String registration(Model model) {
        if (model.getAttribute(USER_DTO) == null)
            model.addAttribute(USER_DTO, UserDTO.builder().build());
        return REGISTRATION;
    }

    @PostMapping("/registration/signup")
    public String createNewUser(@ModelAttribute(USER_DTO) UserDTO userDTO, RedirectAttributes redirectAttributes) {
        if (userDTO.getPassword().length() < 8) {
            redirectAttributes.addFlashAttribute(USER_DTO, userDTO);
            return REDIRECT_REGISTRATION + "?incorectPassword";
        }
        final Response response = authService.makeSignup(userDTO);
        if (Objects.equals(response.getErrorNum(), 520L)) {
            return REDIRECT_REGISTRATION + "?userExist";
        }
        return REDIRECT_LOGIN + "?userCreated&userName=" + userDTO.getUserLogin();
    }


}
