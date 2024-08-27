package com.example.communigate.ximss.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ICalendar {
    @JacksonXmlProperty(localName = "vcalendar")
    private VCalendar vCalendar;
}
