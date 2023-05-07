package com.example.diplom.service;

import com.example.diplom.dto.ChatDTO;
import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.FileList;
import com.example.diplom.ximss.request.FileRead;
import com.example.diplom.ximss.request.FileRemove;
import com.example.diplom.ximss.request.SendIm;
import com.example.diplom.ximss.response.FileData;
import com.example.diplom.ximss.response.FileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final XimssService ximssService;

    private final UserCache userCache;

    private static final String FILE_IM_1_PATH_PATTERN = "private/IM/%s@ivanov.ru.log";

    private static final String FILE_IM_2_PATH_PATTERN = "private/IM/%s@ivanov.log";

    private static final String FILE_IM_3_PATH_PATTERN = "private/IM/%s.log";


    public List<ChatDTO> getAllChats() {
        final List<FileInfo> fileInfos = ximssService.sendRequestToGetList(FileList.builder().build(), FileInfo.class);
        return fileInfos.stream()
            .filter(fi -> fi.getFileName().contains("ivanov.ru"))
            .map(fi -> ximssService.sendRequestToGetObject(FileRead.builder().fileName(fi.getDirectory() + "/" + fi.getFileName()).build(), FileData.class))
            .map(el -> new ChatDTO(el, userCache.getCurrentUserName()))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public ChatDTO getChatByLogin(final String userLogin) {
        return new ChatDTO(ximssService.sendRequestToGetObject(FileRead.builder().fileName(String.format(FILE_IM_1_PATH_PATTERN, userLogin)).build(), FileData.class), userCache.getCurrentUserName());
    }


    public void sendMessage(final String messageText, final String userLogin) {
        ximssService.sendRequestToGetNothing(SendIm.builder().peer(userLogin + "@ivanov.ru").messageText(messageText).build());
    }

    public void deleteChats(List<String> selectedChats) {
        selectedChats.forEach(selectedChat -> {
            ximssService.sendRequestToGetNothing(FileRemove.builder().fileName(String.format(FILE_IM_1_PATH_PATTERN, selectedChat)).build());
            ximssService.sendRequestToGetNothing(FileRemove.builder().fileName(String.format(FILE_IM_2_PATH_PATTERN, selectedChat)).build());
            ximssService.sendRequestToGetNothing(FileRemove.builder().fileName(String.format(FILE_IM_3_PATH_PATTERN, selectedChat)).build());
        });
    }
}
