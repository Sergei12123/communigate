package com.example.communigate.service;

import com.example.communigate.dto.MessageDTO;
import com.example.communigate.manager.XimssService;
import com.example.communigate.text_categorize.TextCategorizerService;
import com.example.communigate.ximss.parts_of_ximss.XimssAddress;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.FolderReadMode;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.MimeMessageSubtype;
import com.example.communigate.ximss.parts_of_ximss.ximss_dictionary.MimeMessageType;
import com.example.communigate.ximss.request.*;
import com.example.communigate.ximss.response.FolderMessage;
import com.example.communigate.ximss.response.FolderReport;
import com.example.communigate.ximss.response.Mime;
import com.example.communigate.ximss.response_request.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    public static final String SUPER_CLIENT = "SuperClient v7.77";

    private final XimssService ximssService;

    private final TextCategorizerService textCategorizerService;

    private final UserCache userCache;


    public List<MessageDTO> getInboxMessages() {
        ximssService.sendRequestToGetNothing(FolderOpen.builder().build());
        final List<FolderReport> folderReports = ximssService.sendRequestToGetList(FolderBrowse.builder().build(), FolderReport.class);
        final List<Map.Entry<Long, MimeMessageParser>> folderMessages = folderReports.stream()
                .map(FolderReport::getUid)
                .map(this::getMessageEntry)
                .toList();
        ximssService.sendRequestToGetNothing(FolderClose.builder().build());
        return folderMessages.stream()
                .map(messageById -> getMessageDTO(
                        messageById,
                        getFlags(folderReports, messageById)))
                .toList();
    }

    private static Set<String> getFlags(List<FolderReport> folderReports, Map.Entry<Long, MimeMessageParser> messageById) {
        return folderReports.stream()
                .filter(el -> el.getUid().equals(messageById.getKey()))
                .findAny()
                .map(FolderReport::getFlag)
                .map(el -> el.split(","))
                .map(Set::of)
                .orElse(Set.of(""));
    }

    public MessageDTO getMessageByUid(final Long uid) {
        ximssService.sendRequestToGetNothing(FolderOpen.builder().build());
        final List<FolderReport> folderReports = ximssService.sendRequestToGetList(FolderBrowse.builder().build(), FolderReport.class);
        Map.Entry<Long, MimeMessageParser> messageById = getMessageEntry(uid);
        ximssService.sendRequestToGetNothing(FolderClose.builder().build());
        return getMessageDTO(messageById, getFlags(folderReports, messageById));
    }

    private MessageDTO getMessageDTO(Map.Entry<Long, MimeMessageParser> messageById, Set<String> flags) {
        try {
            MessageDTO messageDTO = new MessageDTO(messageById.getKey(), messageById.getValue());
            switch (textCategorizerService.classifyTexts(messageDTO.getText())) {
                case TASK -> messageDTO.setHaveTask(true);
                case MEETING -> messageDTO.setHaveMeeting(true);
                case TASK_AND_MEETING -> {
                    messageDTO.setHaveMeeting(true);
                    messageDTO.setHaveTask(true);
                }
                default -> {
                    messageDTO.setHaveMeeting(false);
                    messageDTO.setHaveTask(false);
                }
            }
            if (flags.contains("meetingCreated")) {
                messageDTO.setHaveMeeting(false);
            }
            if (flags.contains("taskCreated")) {
                messageDTO.setHaveTask(false);
            }
            return messageDTO;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public void sendMessage(final MessageDTO messageDTO) {
        ximssService.sendRequestToGetNothing(MessageSubmit.builder().email(
                Email.builder()
                        .from(new XimssAddress(userCache.getCurrentUserName() + "@ivanov.ru"))
                        .subject(messageDTO.getTitle())
                        .to(new XimssAddress(messageDTO.getUserLogin() + "@ivanov.ru"))
                        .xMailer(SUPER_CLIENT)
                        .mime(
                                Mime.builder()
                                        .type(MimeMessageType.TEXT)
                                        .subtype(MimeMessageSubtype.PLAIN)
                                        .messageText(messageDTO.getText())
                                        .build()
                        )
                        .build()
        ).build());
    }

    public void replyFromMessage(final MessageDTO messageDTO) {
        ximssService.sendRequestToGetNothing(FolderOpen.builder().build());
        final Email email = ximssService.sendRequestToGetObject(FolderRead.builder().uid(messageDTO.getUid()).mode(FolderReadMode.REPLY_FROM).build(), FolderMessage.class).getEmail();
        email.getMime().addText(messageDTO.getText());
        ximssService.sendRequestToGetNothing(MessageSubmit.builder().email(email).build());
        ximssService.sendRequestToGetNothing(FolderClose.builder().build());
    }

    public void forwardMessage(final MessageDTO messageDTO) {
        ximssService.sendRequestToGetNothing(FolderOpen.builder().build());
        final Email email = ximssService.sendRequestToGetObject(FolderRead.builder().uid(messageDTO.getUid()).mode(FolderReadMode.FORWARD).build(), FolderMessage.class).getEmail();
        email.getMime().addText(messageDTO.getText());
        email.setTo(new XimssAddress(messageDTO.getUserLogin()));
        ximssService.sendRequestToGetNothing(MessageSubmit.builder().email(email).build());
        ximssService.sendRequestToGetNothing(FolderClose.builder().build());
    }

    public void deleteMessages(final Long[] selectedMessages) {
        markMessage(Arrays.stream(selectedMessages).toList(), "Deleted");
    }

    public void markMessage(final List<Long> uids, final String flag) {
        ximssService.sendRequestToGetNothing(FolderOpen.builder().build());
        ximssService.sendRequestToGetNothing(MessageMark.builder().flags(flag).uid(uids).build());
        ximssService.sendRequestToGetNothing(FolderExpunge.builder().build());
        ximssService.sendRequestToGetNothing(FolderClose.builder().build());
    }

    private Map.Entry<Long, MimeMessageParser> getMessageEntry(final Long uid) {
        try {
            return new AbstractMap.SimpleEntry<>(uid, new MimeMessageParser(new MimeMessage(null, new ByteArrayInputStream(ximssService.getMessageById(uid).getBytes(StandardCharsets.UTF_8)))));
        } catch (MessagingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
