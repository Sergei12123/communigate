package com.example.diplom.ximss.response_request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organizer {

    @JacksonXmlProperty(isAttribute = true, localName = "CN")
    private String commonName;

    @JacksonXmlText
    private String email;
}
