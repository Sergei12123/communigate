package com.example.communigate.ximss.response;

import com.example.communigate.ximss.BaseXIMSSResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "events")
public class Meetings extends BaseXIMSSResponse {


    @JacksonXmlProperty(isAttribute = true)
    private String calendar;

    @JacksonXmlProperty(isAttribute = true, localName = "timeFrom")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss")
    private LocalDateTime timeFrom;

    @JacksonXmlProperty(isAttribute = true, localName = "timeTill")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss")
    private LocalDateTime timeTill;

    @JacksonXmlProperty(isAttribute = true)
    private Integer items;

    @JacksonXmlProperty(localName = "event")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Meeting> meetingList;

    @JacksonXmlText
    private String text = "";

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meeting {

        @JacksonXmlProperty(isAttribute = true, localName = "UID")
        private Long uid;

        @JacksonXmlProperty(isAttribute = true, localName = "itemUID")
        private String itemUid;

        @JacksonXmlProperty(isAttribute = true)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
        private LocalDateTime dateFrom;

        @JacksonXmlProperty(isAttribute = true)
        private Long duration;

        @JacksonXmlProperty(isAttribute = true)
        private String busystatus;

        @JacksonXmlProperty(isAttribute = true)
        private String priority;

        @JacksonXmlProperty(isAttribute = true)
        private String organizer;

        @JacksonXmlProperty(isAttribute = true)
        private Long attendees;

        @JacksonXmlProperty
        private String summary;


    }
}
