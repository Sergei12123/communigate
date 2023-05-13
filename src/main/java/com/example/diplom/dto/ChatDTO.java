package com.example.diplom.dto;

import com.example.diplom.dto.parts.ChatMessage;
import com.example.diplom.ximss.response.FileData;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

    private boolean current;

    private boolean selected;

    private String shortUserLogin;

    private String userLogin;

    private String shortCurrentUserLogin;

    private List<ChatMessage> chatMessages;

    public ChatMessage getLastMessage() {
        return chatMessages == null || chatMessages.size() == 0 ? ChatMessage.builder().build() : chatMessages.get(chatMessages.size() - 1);
    }

    public ChatDTO(final FileData fileData, final String currentUserLogin) {
        System.out.println();
        this.userLogin = fileData.getFileName().replace("private/IM/", "").replace(".log", "").replace("@ivanov.ru", "");
        this.chatMessages = Arrays.stream(fileData.getFileData().split("\n"))
            .map(el -> ChatMessage.builder()
                .messageText(
                    el.substring(el.indexOf(el.charAt(16)) + 1)
                        .replace("*This message was transferred with a trial version of CommuniGate(r) Pro*", "")
                        .replace("\t", "")
                        .replace("\\e", "\n")
                        .trim())
                .userLogin(el.charAt(16) == '<' ? userLogin : currentUserLogin)
                .dateTime(LocalDateTime.parse(el.substring(0, 16).trim(), DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")))
                .build())
            .peek(chatMessage -> {
                if (chatMessage.getMessageText().startsWith("\""))
                    chatMessage.setMessageText(chatMessage.getMessageText().replaceFirst("\"", ""));
                if (chatMessage.getMessageText().endsWith("\""))
                    chatMessage.setMessageText(chatMessage.getMessageText().substring(0, chatMessage.getMessageText().length() - 1));
                chatMessage.setMessageText(chatMessage.getMessageText().trim());
                if (chatMessage.getMessageText().length() > 50) {
                    chatMessage.setShortMessageText(chatMessage.getMessageText().substring(0, 50) + "...");
                } else {
                    chatMessage.setShortMessageText(chatMessage.getMessageText());
                }
            })
            .toList();
        this.shortUserLogin = getShortLogin(userLogin);
        this.shortCurrentUserLogin = getShortLogin(currentUserLogin);
    }

    public static String getShortLogin(final String userLogin) {
        return userLogin.length() > 2 ? userLogin.substring(0, 2) : userLogin.substring(0, 1);
    }

}


