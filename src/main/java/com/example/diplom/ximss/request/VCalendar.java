package com.example.diplom.ximss.request;

import com.example.diplom.ximss.response_request.VEvent;
import com.example.diplom.ximss.response_request.VTodo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VCalendar {
    @JacksonXmlProperty(localName = "vtodo")
    private VTodo vTodo;

    @JacksonXmlProperty(localName = "vevent")
    private VEvent vEvent;
}
