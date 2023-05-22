package com.example.diplom.dictionary;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.stream.Collectors.toCollection;

public enum FolderName {
    INBOX("INBOX"),
    TASKS("Tasks"),
    MEETINGS("Meetings");

    @Getter
    private final String value;

    FolderName(String value) {
        this.value = value;
    }

    public static ArrayList<String> getFolderNames() {
        return Arrays.stream(FolderName.values()).map(FolderName::getValue).collect(toCollection(ArrayList::new));
    }

}
