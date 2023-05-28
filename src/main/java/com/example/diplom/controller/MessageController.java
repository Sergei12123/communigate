package com.example.diplom.controller;

import com.example.diplom.dto.MeetingDTO;
import com.example.diplom.dto.MessageDTO;
import com.example.diplom.dto.MessagesForm;
import com.example.diplom.dto.TaskDTO;
import com.example.diplom.service.MeetingService;
import com.example.diplom.service.MessageService;
import com.example.diplom.service.TaskService;
import com.example.diplom.service.UserCache;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/message")
public class MessageController {

    private final MessageService messageService;

    private final TaskService taskService;

    private final MeetingService meetingService;

    private final UserCache userCache;

    private static final String MESSAGE = "message";

    private static final String NEW_MESSAGE = "new-message";

    private static final String MESSAGES = "messages";

    private static final String MESSAGES_FORM = "messagesForm";


    private static final String REDIRECT_MESSAGE_ALL = "redirect:/message/all";


    @PostMapping("/new")
    public String newMessagePage(Model model) {
        if (model.getAttribute(MESSAGE) == null)
            model.addAttribute(MESSAGE, MessageDTO.builder().build());
        return NEW_MESSAGE;
    }

    @PostMapping("/create")
    public String sendMessage(@ModelAttribute(MESSAGE) MessageDTO messageDTO, RedirectAttributes redirectAttributes) {
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
    public String deleteMessages(@RequestParam(value = "selectedMessages", required = false) Long[] selectedMessages) {
        if (selectedMessages == null) return REDIRECT_MESSAGE_ALL;
        messageService.deleteMessages(selectedMessages);
        return REDIRECT_MESSAGE_ALL + (selectedMessages.length == 1 ? "?messageDeleted" : "?messagesDeleted");
    }

    @RequestMapping("/all")
    public String allMessages(Model model) {
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
        messagesForm.getMessages().stream().filter(el -> el.getUid().equals(uid)).forEach(el -> {
            if (el.getTasks() == null) {
                el.setTasks(new ArrayList<>());
            }
            el.getTasks().add(TaskDTO.builder().build());
        });
        return MESSAGES;
    }

    @PostMapping(value = "/createNewMeeting/{uid}")
    public String createNewMeeting(@PathVariable("uid") Long uid, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        messagesForm.getMessages().stream().filter(el -> el.getUid().equals(uid)).forEach(el -> {
            if (el.getMeetings() == null) {
                el.setMeetings(new ArrayList<>());
            }
            el.getMeetings().add(MeetingDTO.builder().organizer(userCache.getCurrentUserName() + "@ivanov.ru").attendees(List.of("")).build());
        });
        return MESSAGES;
    }

    private Optional<TaskDTO> getLastCreatedTask() {
        return taskService.getAllTasks().stream()
            .filter(el -> el.getTaskText() == null || el.getTaskText().isEmpty())
            .filter(el -> el.getTimeStart() == null)
            .filter(el -> el.getTimeEnd() == null)
            .max(Comparator.comparing(TaskDTO::getTimeCreated));
    }

    private Optional<MeetingDTO> getLastCreatedMeeting() {
        return meetingService.getAllMeetings().stream()
            .filter(el -> el.getMeetingText() == null || el.getMeetingText().isEmpty())
            .filter(el -> el.getTimeEnd() == null)
            .filter(el -> el.getAttendees() == null || el.getAttendees().isEmpty())
            .max(Comparator.comparing(MeetingDTO::getTimeCreated));
    }

    @PostMapping(value = "/{messageUid}/createTask/{taskIndex}")
    public String createTask(@PathVariable("messageUid") Long messageUid, @PathVariable("taskIndex") int taskIndex, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        if (getLastCreatedTask().isEmpty()) {
            taskService.createTask();
        }
        messagesForm.getMessages().stream()
            .filter(messageDTO -> messageDTO.getUid().equals(messageUid))
            .map(MessageDTO::getTasks)
            .forEach(el -> {
                final TaskDTO taskDTOtemp = getLastCreatedTask().get();
                el.get(taskIndex).setTaskUid(taskDTOtemp.getTaskUid());
                el.get(taskIndex).setUid(taskDTOtemp.getUid());
                taskService.updateTask(el.get(taskIndex));
                el.get(taskIndex).setFieldsDisabled(true);
            });
        return MESSAGES;
    }

    @PostMapping(value = "/{messageUid}/deleteTask/{taskIndex}")
    public String deleteTask(@PathVariable("messageUid") Long messageUid, @PathVariable("taskIndex") int taskIndex, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        messagesForm.getMessages().stream()
            .filter(messageDTO -> messageDTO.getUid().equals(messageUid))
            .map(MessageDTO::getTasks)
            .forEach(el -> el.remove(taskIndex));
        return MESSAGES;
    }

    @PostMapping(value = "/{messageUid}/createMeeting/{meetingIndex}")
    public String createMeeting(@PathVariable("messageUid") Long messageUid, @PathVariable("meetingIndex") int meetingIndex, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        if (getLastCreatedMeeting().isEmpty()) {
            meetingService.createMeeting();
        }
        messagesForm.getMessages().stream()
            .filter(messageDTO -> messageDTO.getUid().equals(messageUid))
            .map(MessageDTO::getMeetings)
            .forEach(el -> {
                final MeetingDTO meetingDTOtemp = getLastCreatedMeeting().get();
                el.get(meetingIndex).setItemUid(meetingDTOtemp.getItemUid());
                el.get(meetingIndex).setUid(meetingDTOtemp.getUid());
                meetingService.updateMeeting(el.get(meetingIndex));
                el.get(meetingIndex).setFieldsDisabled(true);
            });
        return MESSAGES;
    }

    @PostMapping(value = "/{messageUid}/deleteMeeting/{meetingIndex}")
    public String deleteMeeting(@PathVariable("messageUid") Long messageUid, @PathVariable("meetingIndex") int meetingIndex, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        messagesForm.getMessages().stream()
            .filter(messageDTO -> messageDTO.getUid().equals(messageUid))
            .map(MessageDTO::getMeetings)
            .forEach(el -> el.remove(meetingIndex));
        return MESSAGES;
    }

    @PostMapping(value = "/{messageUid}/addAttendee/{meetingIndex}")
    public String addAttendee(@PathVariable("messageUid") Long messageUid, @PathVariable("meetingIndex") int meetingIndex, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        messagesForm.getMessages().stream()
            .filter(messageDTO -> messageDTO.getUid().equals(messageUid))
            .map(MessageDTO::getMeetings)
            .forEach(el -> el.get(meetingIndex).getAttendees().add(""));
        return MESSAGES;
    }

    @PostMapping(value = "/{messageUid}/removeAttendee/{meetingIndex}")
    public String removeAttendee(@PathVariable("messageUid") Long messageUid, @PathVariable("meetingIndex") int meetingIndex, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        messagesForm.getMessages().stream()
            .filter(messageDTO -> messageDTO.getUid().equals(messageUid))
            .map(MessageDTO::getMeetings)
            .forEach(el -> el.get(meetingIndex).getAttendees().remove(meetingIndex));
        return MESSAGES;
    }


}
