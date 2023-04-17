package com.example.diplom.ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public enum RulePriority {

    @JsonProperty("Inactive")
    DISABLE("Неактивно"),
    @JsonProperty("1")
    ONE("1"),
    @JsonProperty("2")
    TWO("2"),
    @JsonProperty("3")
    THREE("3"),
    @JsonProperty("4")
    FOUR("4"),
    @JsonProperty("5")
    FIVE("5"),
    @JsonProperty("6")
    SIX("6"),
    @JsonProperty("7")
    SEVEN("7"),
    @JsonProperty("8")
    EIGHT("8"),
    @JsonProperty("9")
    NINE("9"),
    @JsonProperty("Highest")
    MAX("Высокий");

    @Getter
    private final String ruValue;

    RulePriority(String value) {
        this.ruValue = value;
    }

}
