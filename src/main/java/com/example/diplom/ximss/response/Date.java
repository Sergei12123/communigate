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
@JacksonXmlRootElement(localName = "Date")
public class Date {

    @JacksonXmlProperty(isAttribute = true)
    private String localTime;

    @JacksonXmlProperty(isAttribute = true)
    private String timeShift;

    @JacksonXmlText
    private String dateText;
}
