package com.example.communigate.ximss.response_request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendee {

    @JacksonXmlProperty(isAttribute = true, localName = "CN")
    private String commonName;

    @JacksonXmlProperty(isAttribute = true, localName = "ROLE")
    private String role;

    @JacksonXmlProperty(isAttribute = true, localName = "RSVP")
    private String rsvp;

    @JacksonXmlProperty(isAttribute = true, localName = "PARTSTAT")
    private String partstat;

    @JacksonXmlText
    private String email;
}
