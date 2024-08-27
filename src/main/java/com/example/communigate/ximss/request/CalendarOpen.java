package com.example.communigate.ximss.request;

import com.example.communigate.ximss.BaseXIMSSRequest;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "calendarOpen")
public class CalendarOpen extends BaseXIMSSRequest {

    @JacksonXmlProperty(isAttribute = true)
    private String calendar;

}
