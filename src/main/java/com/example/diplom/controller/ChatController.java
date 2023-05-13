package com.example.diplom.controller;

import com.example.diplom.dto.ChatDTO;
import com.example.diplom.dto.parts.ChatMessage;
import com.example.diplom.manager.XimssService;
import com.example.diplom.service.ChatService;
import com.example.diplom.service.UserCache;
import com.example.diplom.ximss.response.ReadIm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.diplom.dto.ChatDTO.getShortLogin;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/chat")
public class ChatController {

    private final ChatService chatService;

    private final XimssService ximssService;

    private final UserCache userCache;

    private static final String CHAT = "chat";

    private static final String CHAT_MESSAGE = "chat_message";

    private static final String CHATS = "chats";

    private static final String REDIRECT_CHAT_ALL = "redirect:/chat/all";

    @ModelAttribute("sessionId")
    public String sessionId() {
        return userCache.getSessionIdForCurrentUser();
    }

    @RequestMapping("/all")
    private String allChats(Model model) {
        List<ChatDTO> allChats = chatService.getAllChats();
        if (allChats == null) allChats = new ArrayList<>();
        model.addAttribute(CHATS, allChats);

        final Object userLogin = model.getAttribute("userLogin");
        final ChatDTO chatDTO;
        if (userLogin != null) {
            chatDTO = allChats.stream().filter(chat -> chat.getUserLogin().equals(userLogin.toString())).findFirst()
                .orElse(ChatDTO.builder()
                    .chatMessages(new ArrayList<>())
                    .userLogin(userLogin.toString())
                    .shortUserLogin(getShortLogin(userLogin.toString()))
                    .shortCurrentUserLogin(getShortLogin(userCache.getCurrentUserName()))
                    .build());
            if (!allChats.contains(chatDTO)) {
                allChats.add(chatDTO);
            }
        } else {
            chatDTO = allChats.size() > 0 ? allChats.get(0) : ChatDTO.builder().build();
        }
        chatDTO.setCurrent(true);

        model.addAttribute(CHAT, chatDTO);
        model.addAttribute(CHAT_MESSAGE, ChatMessage.builder().userLogin(userCache.getCurrentUserName()).build());

        return CHATS;
    }

    @PostMapping("/send")
    private String sendMessage(@RequestParam(value = "messageText", required = false) String messageText,
                               @RequestParam(value = "userLogin", required = false) String userLogin,
                               RedirectAttributes redirectAttributes) {
        if (messageText != null && userLogin != null) {
            chatService.sendMessage(messageText, userLogin);
            redirectAttributes.addFlashAttribute("userLogin", userLogin);
        }
        return REDIRECT_CHAT_ALL;
    }

    @PostMapping("/delete")
    private String deleteSignals(@RequestParam(value = "selectedChats", required = false) List<String> selectedChats,
                                 @RequestParam(value = "userLogin", required = false) String userLogin,
                                 RedirectAttributes redirectAttributes) {
        if (selectedChats == null) {
            redirectAttributes.addFlashAttribute("userLogin", userLogin);
            return REDIRECT_CHAT_ALL;
        }
        chatService.deleteChats(selectedChats);
        return REDIRECT_CHAT_ALL + (selectedChats.size() == 1 ? "?signalDeleted" : "?signalsDeleted");
    }

    @RequestMapping("/new")
    private String newChat(Model model, RedirectAttributes redirectAttributes,
                           @RequestParam(value = "userLogin", required = false) String userLogin,
                           @RequestParam(value = "new", required = false) boolean isNew) {
        if (model.getAttribute("new") == null) {
            return REDIRECT_CHAT_ALL + "?new";
        } else {
            redirectAttributes.addFlashAttribute("userLogin", userLogin);
            return REDIRECT_CHAT_ALL;
        }
    }

    @RequestMapping("/create")
    private String createChat(RedirectAttributes redirectAttributes,
                              @RequestParam(value = "userLogin", required = false) String userLogin) {
        if (userLogin != null) {
            redirectAttributes.addFlashAttribute("userLogin", userLogin.split(",")[0]);
            return REDIRECT_CHAT_ALL;
        } else {
            return REDIRECT_CHAT_ALL + "?new";
        }
    }

    @RequestMapping(value = "/select/{userLogin}")
    public String selectChat(@PathVariable("userLogin") String userLogin, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("userLogin", userLogin.replace("$", ""));
        return REDIRECT_CHAT_ALL;
    }

    @PostMapping("/getChatMessage")
    public String getChatMessages(Model model,
                                  @RequestBody(required = false) String message) {
        ReadIm objectFromXML = ximssService.getObjectFromXML(message, ReadIm.class);
        if (objectFromXML.getMessageText() != null) {
            List<ChatDTO> allChats = chatService.getAllChats();
            String login = objectFromXML.getPeer().replace("@ivanov.ru", "");
            ChatDTO chatByLogin = chatService.getChatByLogin(login);
            chatByLogin.setCurrent(true);
            allChats.stream().filter(el -> el.getUserLogin().equals(chatByLogin.getUserLogin())).forEach(chat -> chat.setCurrent(true));
            String input = objectFromXML.getGmtTime().trim();
            LocalDateTime dateTime = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
            model.addAttribute("chatMessage", ChatMessage.builder()
                .messageText(objectFromXML.getMessageText().replace("*This message was transferred with a trial version of CommuniGate(r) Pro*", ""))
                .userLogin(login)
                .build());
            model.addAttribute(CHAT, chatByLogin);
            model.addAttribute(CHATS, chatService.getAllChats());
            return "chats :: chatMessage";
        } else return "";

    }

    @PostMapping("/getListOfChats")
    public String getListOfChats(Model model,
                                 @RequestBody(required = false) String message) {
        ReadIm objectFromXML = ximssService.getObjectFromXML(message, ReadIm.class);
        if (objectFromXML.getMessageText() != null) {
            List<ChatDTO> allChats = chatService.getAllChats();
            String login = objectFromXML.getPeer().replace("@ivanov.ru", "");
            allChats.stream().filter(el -> el.getUserLogin().equals(login)).forEach(chat -> chat.setCurrent(true));
            model.addAttribute(CHATS, allChats);
            return "chats :: listOfChats";
        } else return "";

    }


}
