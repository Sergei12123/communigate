package com.example.diplom.ximss.response;

import com.example.diplom.ximss.BaseXIMSSResponse;
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
@JacksonXmlRootElement(localName = "tasks")
public class Tasks extends BaseXIMSSResponse {


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

    @JacksonXmlProperty(localName = "task")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Task> taskList;

    @JacksonXmlText
    private String text = "";

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Task {

        @JacksonXmlProperty(isAttribute = true, localName = "UID")
        private Long uid;

        @JacksonXmlProperty(isAttribute = true, localName = "itemUID")
        private String itemUid;

        @JacksonXmlProperty(isAttribute = true, localName = "timeFrom")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss")
        private LocalDateTime taskTimeFrom;

        @JacksonXmlProperty(isAttribute = true, localName = "percent-complete")
        private int percentComplete;

        @JacksonXmlProperty(isAttribute = true)
        private String priority;

        @JacksonXmlProperty(isAttribute = true)
        private String organizer;

        @JacksonXmlProperty
        private String summary;

        @JacksonXmlProperty(isAttribute = true)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss")
        private LocalDateTime due;

    }
}
