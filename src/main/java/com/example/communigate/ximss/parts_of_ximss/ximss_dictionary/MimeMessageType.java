package com.example.communigate.ximss.parts_of_ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MimeMessageType {

    @JsonProperty("multipart")
    MULTIPART,

    @JsonProperty("application")
    APPLICATION,

    @JsonProperty("message")
    MESSAGE,

    @JsonProperty("text")
    TEXT

}
