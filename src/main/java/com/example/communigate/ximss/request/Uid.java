package com.example.communigate.ximss.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Uid {
    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private int from = 0;

    @Builder.Default
    @JacksonXmlProperty(isAttribute = true)
    private int till = 10000;
}
