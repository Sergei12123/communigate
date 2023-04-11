package com.example.diplom.ximss.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "MIME")
public class Mime {

    @JacksonXmlProperty(isAttribute = true)
    private String charset;

    @JacksonXmlProperty(isAttribute = true)
    private int estimatedSize;

    @JacksonXmlProperty(isAttribute = true)
    private String subtype;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true, localName = "Type-format")
    private String typeFormat;

    @JacksonXmlText
    private String messageText;
}
