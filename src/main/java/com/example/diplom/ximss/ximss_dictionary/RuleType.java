package com.example.diplom.ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RuleType {

    @JsonProperty("mailIn")
    MAIL_IN,

    @JsonProperty("signalIn")
    SIGNAL_IN

}
