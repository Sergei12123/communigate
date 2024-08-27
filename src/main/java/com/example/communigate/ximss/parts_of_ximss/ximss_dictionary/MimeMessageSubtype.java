package com.example.communigate.ximss.parts_of_ximss.ximss_dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MimeMessageSubtype {

    @JsonProperty("rfc822")
    RFC_822,

    @JsonProperty("rfc822-headers")
    RFC_822_HEADERS,

    @JsonProperty("directory")
    DIRECTORY,

    @JsonProperty("x-vcard")
    X_VCARD,

    @JsonProperty("x-vgroup")
    X_VGROUP,

    @JsonProperty("calendar")
    CALENDAR,

    @JsonProperty("xml")
    XML,

    @JsonProperty("mixed")
    MIXED,

    @JsonProperty("plain")
    PLAIN

}
