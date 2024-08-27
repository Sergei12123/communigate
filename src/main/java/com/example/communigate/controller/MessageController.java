package com.example.communigate.controller;

import com.example.communigate.dto.MeetingDTO;
import com.example.communigate.dto.MessageDTO;
import com.example.communigate.dto.MessagesForm;
import com.example.communigate.dto.TaskDTO;
import com.example.communigate.service.MeetingService;
import com.example.communigate.service.MessageService;
import com.example.communigate.service.TaskService;
import com.example.communigate.service.UserCache;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/message")
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

    @GetMapping("/all")
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
            el.setHaveTask(false);
            el.getTasks().add(TaskDTO.builder().taskText(el.getTitle()).timeStart(LocalDateTime.now()).timeEnd(LocalDateTime.now().plusDays(1)).build());
        });
        return MESSAGES;
    }

    @PostMapping(value = "/createNewMeeting/{uid}")
    public String createNewMeeting(@PathVariable("uid") Long uid, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        messagesForm.getMessages().stream().filter(el -> el.getUid().equals(uid)).forEach(el -> {
            if (el.getMeetings() == null) {
                el.setMeetings(new ArrayList<>());
            }
            el.setHaveMeeting(false);
            el.getMeetings().add(MeetingDTO.builder()
                    .meetingText(el.getTitle())
                    .attendees(List.of(el.getUserLogin()))
                    .organizer(userCache.getCurrentUserName() + "@ivanov.ru")
                    .timeStart(LocalDateTime.now().plusMinutes(15))
                    .timeEnd(LocalDateTime.now().plusMinutes(45))
                    .build());
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
                .forEach(el -> {
                    final TaskDTO taskDTOtemp = getLastCreatedTask().get();
                    el.getTasks().get(taskIndex).setTaskUid(taskDTOtemp.getTaskUid());
                    el.getTasks().get(taskIndex).setUid(taskDTOtemp.getUid());
                    taskService.updateTask(el.getTasks().get(taskIndex));
                    messageService.markMessage(List.of(el.getUid()), "taskCreated");
                    el.getTasks().get(taskIndex).setFieldsDisabled(true);
                });
        return MESSAGES;
    }

    @PostMapping(value = "/{messageUid}/deleteTask/{taskIndex}")
    public String deleteTask(@PathVariable("messageUid") Long messageUid, @PathVariable("taskIndex") int taskIndex, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        messagesForm.getMessages().stream()
                .filter(messageDTO -> messageDTO.getUid().equals(messageUid))
                .forEach(el -> {
                    el.getTasks().remove(taskIndex);
                    el.setHaveTask(true);
                });

        return MESSAGES;
    }

    @PostMapping(value = "/{messageUid}/createMeeting/{meetingIndex}")
    public String createMeeting(@PathVariable("messageUid") Long messageUid, @PathVariable("meetingIndex") int meetingIndex, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        if (getLastCreatedMeeting().isEmpty()) {
            meetingService.createMeeting();
        }
        messagesForm.getMessages().stream()
                .filter(messageDTO -> messageDTO.getUid().equals(messageUid))
                .forEach(el -> {
                    final MeetingDTO meetingDTOtemp = getLastCreatedMeeting().get();
                    el.getMeetings().get(meetingIndex).setItemUid(meetingDTOtemp.getItemUid());
                    el.getMeetings().get(meetingIndex).setUid(meetingDTOtemp.getUid());
                    meetingService.updateMeeting(el.getMeetings().get(meetingIndex));
                    messageService.markMessage(List.of(el.getUid()), "meetingCreated");
                    el.getMeetings().get(meetingIndex).setFieldsDisabled(true);
                });
        return MESSAGES;
    }

    @PostMapping(value = "/{messageUid}/deleteMeeting/{meetingIndex}")
    public String deleteMeeting(@PathVariable("messageUid") Long messageUid, @PathVariable("meetingIndex") int meetingIndex, @ModelAttribute(MESSAGES_FORM) MessagesForm messagesForm) {
        messagesForm.getMessages().stream()
                .filter(messageDTO -> messageDTO.getUid().equals(messageUid))
                .forEach(el -> {
                    el.getMeetings().remove(meetingIndex);
                    el.setHaveMeeting(true);
                });
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
