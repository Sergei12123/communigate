package com.example.diplom.ximss.parts_of_ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public enum ActionOpCode {

    @JsonProperty("Store in")
    STORE_IN("Записать в"),
    @JsonProperty("Mark")
    MARK("Пометить"),
    @JsonProperty("Add Header")
    ADD_HEADER("Добавить Заголовок"),
    @JsonProperty("Tag Subject")
    TAG_SUBJECT("Пометить Тему"),
    @JsonProperty("Reject with")
    REJECT_WITH("Отвергнуть с"),
    @JsonProperty("Discard")
    DISCARD("Выбросить"),
    @JsonProperty("Stop Processing")
    STOP_PROCESSING("Прекратить Обработку"),
    @JsonProperty("Remember 'From' in")
    REMEMBER_FROM_IN("Запомнить 'От кого'в"),
    @JsonProperty("Accept Request")
    ACCEPT_REQUEST("Принять Приглашение"),
    @JsonProperty("Accept Reply")
    ACCEPT_REPLY("Принять Ответ"),
    @JsonProperty("Store Encrypted in")
    STORE_ENCRYPTED_IN("Записать Зашифровано в"),
    @JsonProperty("Copy Attachments into")
    COPY_ATTACHMENTS_INTO("Копировать Приложения в"),
    @JsonProperty("Redirect To")
    REDIRECT_TO("Перенаправить к"),
    @JsonProperty("Forward to")
    FORWARD_TO("Переслать к"),
    @JsonProperty("Mirror to")
    MIRROR_TO("Переправить к"),
    @JsonProperty("Reply with")
    REPLY_WITH("Ответить"),
    @JsonProperty("Reply to All with")
    REPLY_TO_ALL_WITH("Ответить Всем"),
    @JsonProperty("React with")
    REACT_WITH("Отреагировать с"),
    @JsonProperty("Send IM")
    SEND_IM("Послать IM"),
    @JsonProperty("Send Push")
    SEND_PUSH("Послать Push"),
    @JsonProperty("Execute URL")
    EXECUTE_URL("Обратиться по URL"),
    @JsonProperty("Execute")
    EXECUTE("Выполнить");

    @Getter
    private final String ruValue;

    ActionOpCode(String value) {
        this.ruValue = value;
    }
}
