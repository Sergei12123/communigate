package com.example.diplom.dto;

import com.example.diplom.ximss.response.FolderMessage;
import lombok.*;

import static com.example.diplom.dictionary.MessagePattern.CASUAL_MESSAGE_PATTERN;
import static com.example.diplom.dictionary.MessagePattern.MESSAGE_REPLY_PATTERN;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Long uid;

    public String userLogin;

    public String title;

    public String text;

    public String replyText;

    public boolean selected;

    private boolean reply;

    public MessageDTO(final FolderMessage folderMessage) {
        this.userLogin = folderMessage.getEmail().getFrom();
        this.title = folderMessage.getEmail().getSubject();
        this.uid = folderMessage.getUid();
        this.selected = false;
        final String mimeText = folderMessage.getEmail().getMime().getMessageText();
        if (!MESSAGE_REPLY_PATTERN.getStringValues(mimeText).isEmpty()) {
            this.reply = true;
            this.replyText = MESSAGE_REPLY_PATTERN.getStringValues(mimeText).get(0);
            this.text = MESSAGE_REPLY_PATTERN.getStringValues(mimeText).get(1);
        } else {
            this.reply = false;
            this.text = CASUAL_MESSAGE_PATTERN.getStringValues(mimeText).get(0);
        }
    }
}
