package com.example.diplom.ximss.parts_of_ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FolderReadMode {

    @JsonProperty("replyFrom")
    REPLY_FROM,

    @JsonProperty("replyAll")
    REPLY_ALL,

    @JsonProperty("forward")
    FORWARD,

    @JsonProperty("replyFromHTML")
    REPLY_FROM_HTML,

    @JsonProperty("replyAllHTML")
    REPLY_ALL_HTML,

    @JsonProperty("forwardHTML")
    FORWARD_HTML

}
