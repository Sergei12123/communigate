package com.example.communigate.ximss.response;

import com.example.communigate.ximss.BaseXIMSSResponse;
import com.example.communigate.ximss.response_request.VEvent;
import com.example.communigate.ximss.response_request.VTodo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "calendarItem")
public class CalendarItem extends BaseXIMSSResponse {

    @JacksonXmlProperty(isAttribute = true)
    private String calendar;

    @JacksonXmlProperty(isAttribute = true, localName = "UID")
    private Long uid;

    @JacksonXmlProperty(localName = "vtodo")
    private VTodo vTodo;

    @JacksonXmlProperty(localName = "vevent")
    private VEvent vEvent;
}
