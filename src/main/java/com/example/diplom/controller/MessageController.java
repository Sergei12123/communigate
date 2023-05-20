package com.example.diplom.controller;

import com.example.diplom.dto.MessageDTO;
import com.example.diplom.dto.TaskDTO;
import com.example.diplom.service.MessageService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/message")
public class MessageController {

    private final MessageService messageService;

    private final XmlMapper xmlMapper;

    private static final String MESSAGE = "message";

    private static final String NEW_MESSAGE = "new-message";

    private static final String MESSAGES = "messages";

    private static final String REDIRECT_MESSAGE_ALL = "redirect:/message/all";


    @GetMapping("/new")
    private String newMessagePage(Model model) {
        if (model.getAttribute(MESSAGE) == null)
            model.addAttribute(MESSAGE, MessageDTO.builder().build());
        return NEW_MESSAGE;
    }

    @PostMapping("/addTask")
    private String addTask(@ModelAttribute(MESSAGE) MessageDTO messageDTO) {
        messageDTO.getTasks().add(TaskDTO.builder().build());
        return NEW_MESSAGE;
    }

    @PostMapping("/removeTask")
    private String removeTask(@ModelAttribute(MESSAGE) MessageDTO messageDTO) {
        messageDTO.getTasks().remove(messageDTO.getTasks().size() - 1);
        return NEW_MESSAGE;
    }

    @PostMapping("/create")
    private String sendMessage(@ModelAttribute(MESSAGE) MessageDTO messageDTO, RedirectAttributes redirectAttributes) {
        if (messageDTO.isReply()) {
            messageService.replyFromMessage(messageDTO);
        } else if (messageDTO.isForward()) {
            messageService.forwardMessage(messageDTO);
        } else {
            messageService.sendMessage(messageDTO);
        }
        redirectAttributes.addAttribute("messageSent", true);
        redirectAttributes.addAttribute("userName", messageDTO.getUserLogin());
        return REDIRECT_MESSAGE_ALL;
    }

    @PostMapping("/delete")
    private String deleteMessages(@RequestParam(value = "selectedMessages", required = false) Long[] selectedMessages) {
        if (selectedMessages == null) return REDIRECT_MESSAGE_ALL;
        messageService.deleteMessages(selectedMessages);
        return REDIRECT_MESSAGE_ALL + (selectedMessages.length == 1 ? "?messageDeleted" : "?messagesDeleted");
    }

    @RequestMapping("/all")
    private String allMessages(Model model) {
        model.addAttribute(MESSAGES, messageService.getInboxMessages());
        return MESSAGES;
    }

    @PostMapping(value = "/replyTo/{uid}")
    public String replyToMessage(@PathVariable("uid") Long uid, Model model) {
        final MessageDTO tempDto = messageService.getMessageByUid(uid);

        model.addAttribute(MESSAGE,
            MessageDTO.builder()
                .uid(uid)
                .reply(true)
                .replyText(tempDto.getText())
                .userLogin(tempDto.getUserLogin())
                .replyFrom(tempDto.getUserLogin())
                .replyTitle(tempDto.getReplyTitle())
                .title(tempDto.getTitle())
                .build());
        return NEW_MESSAGE;
    }

    @PostMapping(value = "/forwardTo/{uid}")
    public String forwardToMessage(@PathVariable("uid") Long uid, Model model) {
        final MessageDTO tempDto = messageService.getMessageByUid(uid);

        model.addAttribute(MESSAGE,
            MessageDTO.builder()
                .uid(uid)
                .forward(true)
                .replyText(tempDto.getText())
                .replyTitle(tempDto.getReplyTitle())
                .replyFrom(tempDto.getUserLogin())
                .title(tempDto.getTitle())
                .build());
        return NEW_MESSAGE;
    }

}
