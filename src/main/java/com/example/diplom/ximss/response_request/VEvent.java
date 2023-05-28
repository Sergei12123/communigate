package com.example.diplom.ximss.response_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class VEvent {

    @JacksonXmlProperty(isAttribute = true)
    private Organizer organizer;

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime dtstamp;

    @JacksonXmlProperty(localName = "uid")
    private String uid;

    @JacksonXmlProperty
    private Integer sequence;

    @JacksonXmlProperty
    private String summary;

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime lastModified;

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime created;

    @JacksonXmlProperty
    private Integer priority;

    @JacksonXmlProperty
    private String busyStatus;

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime dtstart;

    @JacksonXmlProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss'Z'")
    private LocalDateTime dtend;

    @JacksonXmlProperty(localName = "attendee")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Attendee> attendees;

    @JsonIgnore
    public List<String> getStringAttendees() {
        return attendees == null ? new ArrayList<>() : attendees.stream().map(Attendee::getCommonName).toList();
    }
}

