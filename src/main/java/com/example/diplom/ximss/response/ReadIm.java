package com.example.diplom.ximss.response;

import com.example.diplom.ximss.BaseXIMSSResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "readIM")
public class ReadIm extends BaseXIMSSResponse {

    @JacksonXmlProperty(isAttribute = true)
    private String peer;

    @JacksonXmlProperty(isAttribute = true)
    private String peerName;

    @JacksonXmlProperty(isAttribute = true)
    private String clientID;

    @JacksonXmlProperty(isAttribute = true)
    private String iqid;

    @JacksonXmlProperty(isAttribute = true)
    private String gmtTime;

    @JacksonXmlProperty
    private String composing;

    @JacksonXmlProperty
    private String paused;

    @JacksonXmlProperty
    private String gone;

    @JacksonXmlProperty
    private String body;

    @JacksonXmlProperty
    private String subject;

    @JacksonXmlText
    private String messageText;

}
