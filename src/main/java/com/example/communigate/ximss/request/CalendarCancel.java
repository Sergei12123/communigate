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
@JacksonXmlRootElement(localName = "calendarCancel")
public class CalendarCancel extends BaseXIMSSRequest {

    @JacksonXmlProperty(isAttribute = true)
    private String calendar;

    @JacksonXmlProperty(isAttribute = true)
    private String itemUid;

    @JacksonXmlProperty(localName = "icalendar")
    private ICalendar iCalendar;

}
