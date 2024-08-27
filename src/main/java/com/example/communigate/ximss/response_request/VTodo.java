package com.example.communigate.ximss.response_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class VTodo {

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime dtstamp;

    @JacksonXmlProperty
    private String uid;

    @JacksonXmlProperty
    private Integer sequence;

    @JacksonXmlProperty
    private String summary;

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime dtstart;

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime due;

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime lastModified;

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime created;

    @JacksonXmlProperty
    private Integer priority;

    @JacksonXmlProperty(localName = "percent-complete")
    private Integer percentComplete;
}
