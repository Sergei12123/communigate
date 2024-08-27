package com.example.communigate.ximss.parts_of_ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

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
    REMEMBER_FROM_IN("Запомнить 'От кого' в"),
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
    @JsonProperty("SendIM")
    SEND_IM("Послать IM"),
    @JsonProperty("SendPush")
    SEND_PUSH("Послать Push"),
    @JsonProperty("SendURL")
    EXECUTE_URL("Обратиться по URL"),
    @JsonProperty("Execute")
    EXECUTE("Выполнить"),

    @JsonProperty("Fork to")
    FORK_TO("Подключить"),

    @JsonProperty("Discard Rules")
    DISCARD_RULES("Остановить Правила"),

    @JsonProperty("Write To Log")
    WRITE_TO_LOG("Записать в Журнал");

    @Getter
    private final String ruValue;

    ActionOpCode(String value) {
        this.ruValue = value;
    }

    public static final List<ActionOpCode> MAIL_ACTION_OP_CODES = List.of(
            STORE_IN,
            MARK,
            ADD_HEADER,
            TAG_SUBJECT,
            REJECT_WITH,
            DISCARD,
            STOP_PROCESSING,
            REMEMBER_FROM_IN,
            ACCEPT_REQUEST,
            ACCEPT_REPLY,
            STORE_ENCRYPTED_IN,
            COPY_ATTACHMENTS_INTO,
            REDIRECT_TO,
            FORWARD_TO,
            MIRROR_TO,
            REPLY_WITH,
            REPLY_TO_ALL_WITH,
            REACT_WITH,
            SEND_IM,
            SEND_PUSH,
            EXECUTE_URL,
            EXECUTE
    );

    public static final List<ActionOpCode> SIGNAL_ACTION_OP_CODES = List.of(
            REJECT_WITH,
            REDIRECT_TO,
            FORK_TO,
            STOP_PROCESSING,
            DISCARD_RULES,
            REMEMBER_FROM_IN,
            SEND_IM,
            SEND_PUSH,
            EXECUTE_URL,
            WRITE_TO_LOG
    );
}
