package com.example.diplom.ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

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
    CALENDAR_METHOD("Календарный Запрос");

    @Getter
    private final String ruValue;

    ConditionOpCode(String value) {
        this.ruValue = value;
    }
}
