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

import java.util.ArrayList;
import java.util.List;

import static com.example.diplom.dto.ChatDTO.getShortLogin;

@Controller
@AllArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    public static final String USER_LOGIN = "userLogin";
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

    @GetMapping("/all")
    public String allChats(Model model) {
        List<ChatDTO> allChats = chatService.getAllChats();
        if (allChats == null) allChats = new ArrayList<>();
        model.addAttribute(CHATS, allChats);

        final Object userLogin = model.getAttribute(USER_LOGIN);
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
            chatDTO = !allChats.isEmpty() ? allChats.get(0) : ChatDTO.builder().build();
        }
        chatDTO.setCurrent(true);

        model.addAttribute(CHAT, chatDTO);
        model.addAttribute(CHAT_MESSAGE, ChatMessage.builder().userLogin(userCache.getCurrentUserName()).build());

        return CHATS;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam(value = "messageText", required = false) String messageText,
                              @RequestParam(value = USER_LOGIN, required = false) String userLogin,
                              RedirectAttributes redirectAttributes) {
        if (messageText != null && userLogin != null) {
            chatService.sendMessage(messageText, userLogin);
            redirectAttributes.addFlashAttribute(USER_LOGIN, userLogin);
        }
        return REDIRECT_CHAT_ALL;
    }

    @PostMapping("/delete")
    public String deleteSignals(@RequestParam(value = "selectedChats", required = false) List<String> selectedChats,
                                @RequestParam(value = USER_LOGIN, required = false) String userLogin,
                                RedirectAttributes redirectAttributes) {
        if (selectedChats == null) {
            redirectAttributes.addFlashAttribute(USER_LOGIN, userLogin);
            return REDIRECT_CHAT_ALL;
        }
        chatService.deleteChats(selectedChats);
        return REDIRECT_CHAT_ALL + (selectedChats.size() == 1 ? "?signalDeleted" : "?signalsDeleted");
    }

    @GetMapping("/new")
    public String newChat(Model model, RedirectAttributes redirectAttributes,
                          @RequestParam(value = USER_LOGIN, required = false) String userLogin) {
        if (model.getAttribute("new") == null) {
            return REDIRECT_CHAT_ALL + "?new";
        } else {
            redirectAttributes.addFlashAttribute(USER_LOGIN, userLogin);
            return REDIRECT_CHAT_ALL;
        }
    }

    @PostMapping("/create")
    public String createChat(RedirectAttributes redirectAttributes,
                             @RequestParam(value = USER_LOGIN, required = false) String userLogin) {
        if (userLogin != null) {
            redirectAttributes.addFlashAttribute(USER_LOGIN, userLogin.split(",")[0]);
            return REDIRECT_CHAT_ALL;
        } else {
            return REDIRECT_CHAT_ALL + "?new";
        }
    }

    @GetMapping("/select/{userLogin}")
    public String selectChat(@PathVariable(USER_LOGIN) String userLogin, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(USER_LOGIN, userLogin.replace("$", ""));
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
