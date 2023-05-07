package com.example.diplom.ximss.parts_of_ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public enum SignalStage {

    @JsonProperty("0")
    IMMEDIATELY("Немедленно"),
    @JsonProperty("1")
    SEC_1("1 сек"),
    @JsonProperty("2")
    SEC_2("2 сек"),
    @JsonProperty("3")
    SEC_3("3 сек"),
    @JsonProperty("4")
    SEC_4("4 сек"),
    @JsonProperty("5")
    SEC_5("5 сек"),
    @JsonProperty("7")
    SEC_7("7 сек"),
    @JsonProperty("10")
    SEC_10("10 сек"),
    @JsonProperty("15")
    SEC_15("15 сек"),
    @JsonProperty("20")
    SEC_20("20 сек"),
    @JsonProperty("25")
    SEC_25("25 сек"),
    @JsonProperty("30")
    SEC_30("30 сек"),
    @JsonProperty("40")
    SEC_40("40 сек"),
    @JsonProperty("60")
    SEC_60("60 сек"),
    @JsonProperty("90")
    SEC_90("90 сек"),
    @JsonProperty("120")
    MIN_2("2 мин"),
    @JsonProperty("600")
    MIN_10("10 мин"),
    @JsonProperty("995")
    NO_ANSWER("Не отвечает"),
    @JsonProperty("997")
    BUSY("Занято"),
    @JsonProperty("999")
    FAILURE("Ошибка");

    @Getter
    private final String ruValue;

    SignalStage(String value) {
        this.ruValue = value;
    }
}
