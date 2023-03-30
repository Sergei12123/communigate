package com.example.diplom.ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SaslType {

    LOGIN,
    PLAIN,
    @JsonProperty("CRAM-MD5")
    CRAM_MD5,
    @JsonProperty("DIGEST-MD5")
    DIGEST_MD5,
    GSSAPI;

}
