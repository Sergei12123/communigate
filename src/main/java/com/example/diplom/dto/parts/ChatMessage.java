package com.example.diplom.dto.parts;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private String userLogin;

    private String messageText;

    private String shortMessageText;

    private LocalDateTime dateTime;


}
