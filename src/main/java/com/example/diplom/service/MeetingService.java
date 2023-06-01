package com.example.diplom.service;

import com.example.diplom.dictionary.FolderName;
import com.example.diplom.dto.MeetingDTO;
import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.*;
import com.example.diplom.ximss.response.CalendarItem;
import com.example.diplom.ximss.response.Meetings;
import com.example.diplom.ximss.response_request.Attendee;
import com.example.diplom.ximss.response_request.Organizer;
import com.example.diplom.ximss.response_request.VEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final XimssService ximssService;

    private final UserCache userCache;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public List<MeetingDTO> getAllMeetings() {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.MEETINGS.getValue()).build());
        final List<Meetings> meetings = ximssService.sendRequestToGetList(FindEvents.builder()
            .calendar(FolderName.MEETINGS.getValue())
            .timeFrom(LocalDate.now().minusDays(30).format(formatter))
            .timeTill(LocalDate.now().plusDays(30).format(formatter))
            .build(), Meetings.class);

        final List<CalendarItem> calendarItems = meetings.stream()
            .filter(el -> el.getMeetingList() != null)
            .flatMap(meetings1 -> meetings1.getMeetingList().stream())
            .map(Meetings.Meeting::getUid)
            .distinct()
            .map(uid -> ximssService.sendRequestToGetObject(CalendarReadItem.builder().calendar(FolderName.MEETINGS.getValue()).uid(uid).build(), CalendarItem.class))
            .toList();
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.MEETINGS.getValue()).build());
        return calendarItems.stream().map(MeetingDTO::new)
            .sorted(Comparator.comparing(MeetingDTO::getTimeCreated))
            .collect(Collectors.toCollection(ArrayList::new));

    }

    public VEvent getVeventByUid(final Long uid) {
        final CalendarItem calendarItem = ximssService.sendRequestToGetObject(CalendarReadItem.builder().calendar(FolderName.MEETINGS.getValue()).uid(uid).build(), CalendarItem.class);
        return calendarItem.getVEvent();
    }


    public void updateMeetings(final List<MeetingDTO> meetings) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.MEETINGS.getValue()).build());
        meetings.forEach(this::updateMeetingByMeetingDto);
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.MEETINGS.getValue()).build());
    }

    public void updateMeeting(final MeetingDTO taskDTO) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.MEETINGS.getValue()).build());
        updateMeetingByMeetingDto(taskDTO);
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.MEETINGS.getValue()).build());
    }

    private void updateMeetingByMeetingDto(MeetingDTO meetingDTO) {
        final VEvent vevent = getVeventByUid(meetingDTO.getUid());
        vevent.setSummary(meetingDTO.getMeetingText());
        vevent.setAttendees(meetingDTO.getAttendees().stream().map(el -> Attendee.builder().commonName(el).build()).toList());
        vevent.setDtstart(meetingDTO.getTimeStart());
        vevent.setDtend(meetingDTO.getTimeEnd());
        ximssService.sendRequestToGetNothing(
            CalendarPublish.builder()
                .calendar(FolderName.MEETINGS.getValue())
                .iCalendar(new ICalendar(VCalendar.builder().vEvent(vevent).build()))
                .build());
    }

    public void deleteMeetings(final List<MeetingDTO> meetings) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.MEETINGS.getValue()).build());
        meetings.forEach(meetingDTO -> ximssService.sendRequestToGetNothing(
            CalendarCancel.builder()
                .calendar(FolderName.MEETINGS.getValue())
                .itemUid(meetingDTO.getItemUid())
                .build()));
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.MEETINGS.getValue()).build());
    }

    public void createMeeting() {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.MEETINGS.getValue()).build());
        ximssService.sendRequestToGetNothing(
            CalendarPublish.builder()
                .calendar(FolderName.MEETINGS.getValue())
                .iCalendar(new ICalendar(VCalendar.builder().vEvent(VEvent.builder()
                    .organizer(Organizer.builder().commonName(userCache.getCurrentUserName() + "@ivanov.ru").build())
                    .dtstart(LocalDateTime.now().plusMinutes(15))
                    .build()).build()))
                .build());
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.MEETINGS.getValue()).build());
    }
}
