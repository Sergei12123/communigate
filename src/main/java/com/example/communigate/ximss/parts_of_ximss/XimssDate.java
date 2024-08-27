package com.example.communigate.ximss.parts_of_ximss;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class XimssDate {

    @JacksonXmlProperty(isAttribute = true)
    private String localTime;

    @JacksonXmlProperty(isAttribute = true)
    private String timeShift;

    @JacksonXmlText
    private String dateText;
}
