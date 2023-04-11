package com.example.diplom.controller;

import com.example.diplom.manager.SessionService;
import com.example.diplom.manager.XimssService;
import com.example.diplom.service.MessageService;
import com.example.diplom.service.RedisRepository;
import dto.MessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class MessageController {

    private final RedisRepository redisRepository;

    private final MessageService messageService;


    @RequestMapping("/createNewMessage")
    private String createNewMessage(Model model) {
        model.addAttribute("messages", messageService.getInboxEmailsPrewiew());
        return "hello";
    }

    @GetMapping("/new-message")
    private String newMessagePage(Model model) {
        model.addAttribute("message", MessageDTO.builder().build());
        return "new-message";
    }

    @PostMapping("/sendMessage")
    private String sendMessage(@ModelAttribute("message") MessageDTO messageDTO, RedirectAttributes redirectAttributes) {
        messageService.sendMessage(messageDTO);
        redirectAttributes.addAttribute("messageSent");
        redirectAttributes.addAttribute("userName", messageDTO.getUserLogin());
        return "redirect:/hello";
    }

    @PostMapping("/deleteMessages")
    private String deleteMessages(@RequestParam("selectedMessages") Long[] selectedMessages) {
        messageService.deleteMessages(selectedMessages);
        return "redirect:/hello" + (selectedMessages.length == 0 ? null : selectedMessages.length == 1 ? "?messageDeleted" : "?messagesDeleted");
    }

    @RequestMapping("/hello")
    private String helloPage(Model model) {
        model.addAttribute("messages", messageService.getInboxEmailsPrewiew());
        return "hello";
    }

}
