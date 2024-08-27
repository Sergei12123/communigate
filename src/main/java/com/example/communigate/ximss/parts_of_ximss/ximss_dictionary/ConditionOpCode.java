package com.example.communigate.ximss.parts_of_ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public enum ConditionOpCode {

    @JsonProperty("From")
    FROM("От Кого"),
    @JsonProperty("Sender")
    SENDER("Отправитель"),
    @JsonProperty("Return-Path")
    RETURN_PATH("Обратный Адрес"),
    @JsonProperty("To")
    TO("Кому"),
    @JsonProperty("Cc")
    CC("Копия"),
    @JsonProperty("Any To or Cc")
    ANY_TO_OR_CC("Любой Кому/Копия"),
    @JsonProperty("Each To or Cc")
    EACH_TO_OR_CC("Каждый Кому/Копия"),
    @JsonProperty("Reply-To")
    REPLY_TO("Адрес Ответа"),
    @JsonProperty("'From' Name")
    FROM_NAME("Имя Автора"),
    @JsonProperty("Subject")
    SUBJECT("Тема"),
    @JsonProperty("Message-ID")
    MESSAGE_ID("Message-ID"),
    @JsonProperty("Message Size")
    MESSAGE_SIZE("Размер Письма"),
    @JsonProperty("Time Of Day")
    TIME_OF_DAY("Время Суток"),
    @JsonProperty("Current Date")
    CURRENT_DATE("Текущая Дата"),
    @JsonProperty("Current Day")
    CURRENT_DAY("Текущий День"),
    @JsonProperty("Preference")
    PREFERENCE("Настройка"),
    @JsonProperty("FreeBusy")
    FREE_BUSY("Календарная Занятость"),
    @JsonProperty("Human Generated")
    HUMAN_GENERATED("Отправлено вручную"),
    @JsonProperty("Header Field")
    HEADER_FIELD("Поле Заголовка"),
    @JsonProperty("Any Recipient")
    ANY_RECIPIENT("Любой Получатель"),
    @JsonProperty("Each Recipient")
    EACH_RECIPIENT("Каждый Получатель"),
    @JsonProperty("Existing Mailbox")
    EXISTING_MAILBOX("Имеется Папка"),
    @JsonProperty("Security")
    SECURITY("Защита"),
    @JsonProperty("Source")
    SOURCE("Источник"),
    @JsonProperty("Submit Address")
    SUBMIT_ADDRESS("От сетевого адреса"),
    @JsonProperty("Calendar Method")
    CALENDAR_METHOD("Календарный Запрос"),
    @JsonProperty("Method")
    OPERATION("Операция"),
    @JsonProperty("RequestURI")
    TARGET_ADDRESS("Адрес Запроса"),
    @JsonProperty("CallType")
    CALL_TYPE("Тип Вызова"),
    @JsonProperty("RegClients")
    ACTIVE_DEVICES("Активные Устройства"),
    @JsonProperty("ReqField")
    REQUEST_FIELD("Поле Запроса"),
    @JsonProperty("AgentType")
    DEVICE_TYPE("Тип Устройства"),
    @JsonProperty("Presence")
    PRESENCE("Состояние"),
    @JsonProperty("Authenticated")
    AUTHENTICATED("Аутентификация");

    @Getter
    private final String ruValue;

    ConditionOpCode(String value) {
        this.ruValue = value;
    }

    public static final List<ConditionOpCode> MAIL_RULE_OP_CODES = List.of(
            FROM,
            SENDER,
            RETURN_PATH,
            TO,
            CC,
            ANY_TO_OR_CC,
            EACH_TO_OR_CC,
            REPLY_TO,
            FROM_NAME,
            SUBJECT,
            MESSAGE_ID,
            MESSAGE_SIZE,
            TIME_OF_DAY,
            CURRENT_DATE,
            CURRENT_DAY,
            PREFERENCE,
            FREE_BUSY,
            HUMAN_GENERATED,
            HEADER_FIELD,
            ANY_RECIPIENT,
            EACH_RECIPIENT,
            EXISTING_MAILBOX,
            SECURITY,
            SOURCE,
            SUBMIT_ADDRESS,
            CALENDAR_METHOD
    );

    public static final List<ConditionOpCode> SIGNAL_RULE_OP_CODES = List.of(
            OPERATION,
            TARGET_ADDRESS,
            CALL_TYPE,
            ACTIVE_DEVICES,
            REQUEST_FIELD,
            DEVICE_TYPE,
            FROM,
            TO,
            FROM_NAME,
            AUTHENTICATED,
            SUBMIT_ADDRESS,
            PRESENCE,
            TIME_OF_DAY,
            CURRENT_DATE,
            CURRENT_DAY,
            PREFERENCE,
            FREE_BUSY
    );

}
