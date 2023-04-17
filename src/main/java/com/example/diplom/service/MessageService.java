package com.example.diplom.service;

import com.example.diplom.dto.MessageDTO;
import com.example.diplom.manager.SessionService;
import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.*;
import com.example.diplom.ximss.response.FolderMessage;
import com.example.diplom.ximss.response.FolderReport;
import com.example.diplom.ximss.response.Mime;
import com.example.diplom.ximss.response_request.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final XimssService ximssService;

    private final SessionService sessionService;

    public List<MessageDTO> getInboxMessages() {
        ximssService.sendRequestToGetNothing(FolderOpen.builder().build());
        final List<FolderReport> folderReports = ximssService.sendRequestToGetList(FolderBrowse.builder().build(), FolderReport.class);
        final List<FolderMessage> folderMessages = folderReports.stream()
                .map(FolderReport::getUid)
                .map(uid -> ximssService.sendRequestToGetObject(FolderRead.builder().uid(uid).build(), FolderMessage.class))
                .toList();
        ximssService.sendRequestToGetNothing(FolderClose.builder().build());
        return folderMessages.stream()
                .map(MessageDTO::new).toList();

    }


    public void sendMessage(final MessageDTO messageDTO) {
        ximssService.sendRequestToGetNothing(MessageSubmit.builder().email(
                Email.builder()
                        .from(sessionService.getCurrentUserName())
                        .subject(messageDTO.getTitle())
                        .to(messageDTO.getUserLogin())
                        .xMailer("SuperClient v7.77")
                        .mime(
                                Mime.builder()
                                        .type("text")
                                        .subtype("plain")
                                        .messageText(messageDTO.getText())
                                        .build()
                        )
                        .build()
        ).build());
    }

    public void deleteMessages(final Long[] selectedMessages) {
        ximssService.sendRequestToGetNothing(FolderOpen.builder().build());
        ximssService.sendRequestToGetNothing(MessageMark.builder().flags("Deleted").uid(Arrays.stream(selectedMessages).toList()).build());
        ximssService.sendRequestToGetNothing(FolderExpunge.builder().build());
        ximssService.sendRequestToGetNothing(FolderClose.builder().build());
    }
}
