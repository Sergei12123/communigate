package com.example.diplom.ximss.request;


import com.example.diplom.ximss.BaseXIMSS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "messageMark")
public class MessageMark extends BaseXIMSS {

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private String folder = "INBOX";

    @JacksonXmlProperty(isAttribute = true)
    private String flags;

    @JacksonXmlProperty(localName = "UID")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Long> uid;

}
