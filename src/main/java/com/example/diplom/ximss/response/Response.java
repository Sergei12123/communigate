package com.example.diplom.ximss.response;

import com.example.diplom.ximss.BaseXIMSS;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "response")
public class Response extends BaseXIMSS {
    @JacksonXmlProperty(isAttribute = true)
    private String errorText;

    @JacksonXmlProperty(isAttribute = true)
    private Long errorNum;
}
