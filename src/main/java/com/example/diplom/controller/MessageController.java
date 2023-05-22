package com.example.diplom.controller;

import com.example.diplom.dto.MessageDTO;
import com.example.diplom.dto.MessagesForm;
import com.example.diplom.dto.TaskDTO;
import com.example.diplom.service.MessageService;
import com.example.diplom.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/message")
public class MessageController {

    private final MessageService messageService;

    private final TaskService taskService;

    private static final String MESSAGE = "message";

    private static final String NEW_MESSAGE = "new-message";

    private static final String MESSAGES = "messages";

    private static final String MESSAGES_FORM = "messagesForm";


    private static final String REDIRECT_MESSAGE_ALL = "redirect:/message/all";


    @PostMapping("/new")
    private String newMessagePage(Model model) {
        if (model.getAttribute(MESSAGE) == null)
            model.addAttribute(MESSAGE, MessageDTO.builder().build());
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
        model.addAttribute(MESSAGES_FORM, MessagesForm.builder().messages(messageService.getInboxMessages()).build());
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

    @PostMapping(value = "/createNewTask/{uid}")
    public String createNewTask(@PathVariable("uid") Long uid, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        TaskDTO taskDTO = getLastCreatedTask()
            .orElseGet(() -> {
                taskService.createTask();
                return getLastCreatedTask()
                    .orElse(null);
            });

        if (taskDTO != null) {
            messagesForm.getMessages().stream().filter(el -> el.getUid().equals(uid)).forEach(el -> el.getTasks().add(taskDTO));
        }
        return MESSAGES;
    }

    private Optional<TaskDTO> getLastCreatedTask() {
        return taskService.getAllTasks().stream()
            .filter(el -> el.getTaskText() == null || el.getTaskText().isEmpty())
            .filter(el -> el.getTimeStart() == null)
            .filter(el -> el.getTimeEnd() == null)
            .max(Comparator.comparing(TaskDTO::getTimeCreated));
    }

    @PostMapping(value = "/createTask/{taskUid}")
    public String createTask(@PathVariable("taskUid") String taskUid, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        taskService.updateTasks(
            messagesForm.getMessages().stream()
                .map(MessageDTO::getTasks)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(el -> el.getTaskUid() != null)
                .filter(task -> task.getTaskUid().equals(taskUid))
                .toList()
        );
        messagesForm.getMessages().stream()
            .map(MessageDTO::getTasks)
            .flatMap(Collection::stream)
            .filter(Objects::nonNull)
            .filter(el -> el.getTaskUid() != null)
            .filter(task -> task.getTaskUid().equals(taskUid))
            .forEach(taskDTO -> taskDTO.setFieldsDisabled(true));
        return MESSAGES;
    }

    @PostMapping(value = "/deleteTask/{taskUid}")
    public String deleteTask(@PathVariable("taskUid") String taskUid, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        List<TaskDTO> taskDTOList = messagesForm.getMessages().stream()
            .map(MessageDTO::getTasks)
            .flatMap(Collection::stream)
            .filter(Objects::nonNull)
            .filter(el -> el.getTaskUid() != null)
            .filter(task -> task.getTaskUid().equals(taskUid))
            .toList();
        taskService.deleteTasks(taskDTOList);
        messagesForm.getMessages().stream().map(MessageDTO::getTasks).forEach(el -> el.removeIf(task -> task.getTaskUid().equals(taskUid)));
        return MESSAGES;
    }

}
