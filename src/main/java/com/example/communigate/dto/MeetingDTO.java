package com.example.communigate.dto;

import com.example.communigate.ximss.response.CalendarItem;
import com.example.communigate.ximss.response_request.VEvent;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDTO {

    private String itemUid;

    private Long uid;

    private String meetingText;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timeEnd;

    private LocalDateTime timeCreated;

    private String organizer;

    private List<String> attendees;

    private boolean selected;

    private boolean fieldsDisabled;

    public MeetingDTO(final CalendarItem calendarItem) {
        if (calendarItem.getVEvent() != null) {
            this.uid = calendarItem.getUid();
            this.itemUid = calendarItem.getVEvent().getUid();
            this.meetingText = calendarItem.getVEvent().getSummary();
            this.timeStart = calendarItem.getVEvent().getDtstart();
            this.timeEnd = calendarItem.getVEvent().getDtend();
            this.timeCreated = calendarItem.getVEvent().getCreated();
            this.attendees = calendarItem.getVEvent().getStringAttendees();
            this.organizer = calendarItem.getVEvent().getOrganizer().getCommonName();
        }
    }

    public MeetingDTO(final VEvent vEvent) {
        this.itemUid = vEvent.getUid();
        this.meetingText = vEvent.getSummary();
        this.timeStart = vEvent.getDtstart();
        this.timeEnd = vEvent.getDtend();
        this.timeCreated = vEvent.getCreated();
        this.attendees = vEvent.getStringAttendees();
        this.organizer = vEvent.getOrganizer().getCommonName();

    }

}
