package com.example.communigate.ximss.parts_of_ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public enum ConditionOperator {

    @JsonProperty("is")
    IS("равно"),
    @JsonProperty("is not")
    IS_NOT("не равно"),
    @JsonProperty("in")
    IN("среди"),
    @JsonProperty("not in")
    NOT_IN("не среди"),
    @JsonProperty("greater than")
    GREATER_THAN("больше чем"),
    @JsonProperty("less than")
    LESS_THAN("меньше чем");

    @Getter
    private final String ruValue;

    ConditionOperator(String value) {
        this.ruValue = value;
    }
}
