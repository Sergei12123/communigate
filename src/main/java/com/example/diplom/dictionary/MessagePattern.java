package com.example.diplom.dictionary;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MessagePattern {

    CASUAL_MESSAGE_PATTERN("messageText:%s", Pattern.compile("^messageText:(.*)")),
    MESSAGE_REPLY_PATTERN("messageReply:%smessageText:%s", Pattern.compile("^messageReply:(.*?)messageText:(.*)"));

    @Getter
    private final String value;

    @Getter
    private final Pattern pattern;

    MessagePattern(String value, Pattern pattern) {
        this.value = value;
        this.pattern = pattern;
    }

    public List<String> getStringValues(final String inputString) {
        final List<String> values = new ArrayList<>();
        final Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                values.add(matcher.group(i));
            }
        }
        return values;
    }

}
