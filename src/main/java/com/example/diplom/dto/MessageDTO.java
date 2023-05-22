package com.example.diplom.dto;

import lombok.*;
import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.Jsoup;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    public final static Pattern REPLY_PATTERN = Pattern.compile("On (.*?)\\r?\\n\\s*<(.*?)> wrote:\\r?\\n(?:>\\s*(.*?)\\r?\\n){2}((?:.*\\r?\\n)*)");

    private Long uid;

    private String userLogin;

    private String title;

    private String text;

    private String replyFrom;

    private String replyText;

    private String replyTitle;

    private List<TaskDTO> tasks;

    private boolean selected;

    private boolean reply;

    private boolean forward;

    private boolean haveTask;

    private boolean haveMeeting;


    public MessageDTO(final Long uid, final MimeMessageParser mimeMessageParser) throws Exception {
        this.tasks = new ArrayList<>();
        this.userLogin = mimeMessageParser.getFrom();
        this.title = mimeMessageParser.getSubject();
        this.uid = uid;
        this.selected = false;
        this.reply = false;
        this.text = new String(getTextFromMessage(mimeMessageParser.getMimeMessage()).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);


        if (title.startsWith("Re:")) {
            this.title = title.replaceFirst("Re: ", "");
            this.replyFrom = text.substring(text.indexOf("<") + 1).substring(0, text.substring(text.indexOf("<") + 1).indexOf(">"));
            this.replyText = getLanstNPartOfText(2);
            this.replyTitle = title;
            this.text = getLanstNPartOfText(1);
            this.reply = true;
        }
        if (title.startsWith("Fwd:")) {
            this.title = title.replaceFirst("Fwd: ", "");
            if (mimeMessageParser.getMimeMessage().getContent() instanceof MimeMultipart) {
                this.replyFrom = ((MimeMessage) ((MimeMultipart) mimeMessageParser.getMimeMessage().getContent()).getBodyPart(1).getContent()).getFrom()[0].toString();
                this.replyText = new String(getTextFromMessage((MimeMessage) ((MimeMultipart) mimeMessageParser.getMimeMessage().getContent()).getBodyPart(1).getContent()).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                this.text = text.substring(text.lastIndexOf("---") + 3);
            } else {
                this.replyFrom = Arrays.stream(text.split("\n"))
                    .filter(el -> el.contains("From:") || el.contains("От Кого:"))
                    .map(el -> el.replace("From:", "").replace("От Кого:", ""))
                    .map(el -> el.replace("<", ""))
                    .map(el -> el.replace(">", ""))
                    .reduce((a, b) -> b).orElse(null);

                this.replyText = getLanstNPartOfText(2);
                this.text = getLanstNPartOfText(1);
            }
            this.replyTitle = title;
            this.forward = true;
        }
        if (this.replyText != null) {
            if (replyText.contains("---")) this.replyText = replyText.substring(replyText.lastIndexOf("---") + 3);
            this.replyText = replyText.replaceAll("\r", "");
            while (replyText.indexOf("\n") == 0) this.replyText = replyText.replaceFirst("\n", "");
            if (!replyText.endsWith("\n")) this.replyText += "\n";

        }
        if (this.text != null) {
            if (text.contains("---")) this.text = text.substring(text.lastIndexOf("---") + 3);

            this.text = text.replaceAll("\r", "").replace("*This message was transferred with a trial version of CommuniGate(r) Pro*", "");
            while (text.indexOf("\n") == 0) this.text = text.replaceFirst("\n", "");

            if (!text.endsWith("\n")) this.text += "\n";
        }


    }

    private String getTextFromMessage(Message message) throws IOException, MessagingException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
        MimeMultipart mimeMultipart) throws IOException, MessagingException {

        int count = mimeMultipart.getCount();
        if (count == 0)
            throw new MessagingException("Multipart with no body parts not supported.");
        boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
        if (multipartAlt)
            // alternatives appear in an order of increasing
            // faithfulness to the original content. Customize as req'd.
            return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
        String result = "";
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            result += getTextFromBodyPart(bodyPart);
        }
        return result;
    }

    private String getTextFromBodyPart(
        BodyPart bodyPart) throws IOException, MessagingException {

        String result = "";
        if (bodyPart.isMimeType("text/plain")) {
            result = (String) bodyPart.getContent();
        } else if (bodyPart.isMimeType("text/html")) {
            String html = (String) bodyPart.getContent();
            result = Jsoup.parse(html).text();
        } else if (bodyPart.getContent() instanceof MimeMultipart) {
            result = getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
        }
        return result;
    }

    private String getLanstNPartOfText(final int n) {
        final List<String> splittedText = Arrays.stream(Arrays.stream(text.split("\r\n"))
                .map(el -> el.trim().startsWith(">") ? el.trim().replaceFirst(">", "") : el.trim())
                .collect(Collectors.joining("\n")).split("(?m)^\\s*$"))
            .filter(s -> !s.isEmpty())
            .toList();
        return splittedText.stream()
            .skip(splittedText.size() - n)
            .map(s -> s.trim().replaceAll("(?m)^\n", ""))
            .map(s -> s.contains("*This message was transferred with a trial") ? s.substring(s.lastIndexOf("Pro*") + 6) : s)
            .findFirst()
            .orElse(null);
    }

}
