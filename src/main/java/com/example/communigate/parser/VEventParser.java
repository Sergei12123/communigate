package com.example.communigate.parser;

import com.example.communigate.ximss.response_request.Attendee;
import com.example.communigate.ximss.response_request.Organizer;
import com.example.communigate.ximss.response_request.VEvent;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VEventParser {

    public static VEvent parseVEvent(String text) {
        VEvent.VEventBuilder builder = VEvent.builder();
        List<Attendee> attendees = new ArrayList<>();

        // Organizer
        String organizerPattern = "ORGANIZER;CN=\"(.+?)\"";
        Pattern pattern = Pattern.compile(organizerPattern);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.organizer(Organizer.builder().commonName(matcher.group(1)).build());
        }

        // DTSTAMP
        String dtstampPattern = "DTSTAMP:(\\d{8}T\\d{6}Z)";
        pattern = Pattern.compile(dtstampPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.dtstamp(parseDateTime(matcher.group(1)));
        }

        // UID
        String uidPattern = "UID:(.+)";
        pattern = Pattern.compile(uidPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.uid(matcher.group(1));
        }

        // SEQUENCE
        String sequencePattern = "SEQUENCE:(\\d+)";
        pattern = Pattern.compile(sequencePattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.sequence(Integer.parseInt(matcher.group(1)));
        }

        // SUMMARY
        String summaryPattern = "SUMMARY:(.+?)DTSTART";
        pattern = Pattern.compile(summaryPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.summary(new String(matcher.group(1).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        }

        // LAST-MODIFIED
        String lastModifiedPattern = "LAST-MODIFIED:(\\d{8}T\\d{6}Z)";
        pattern = Pattern.compile(lastModifiedPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.lastModified(parseDateTime(matcher.group(1)));
        }

        // CREATED
        String createdPattern = "CREATED:(\\d{8}T\\d{6}Z)";
        pattern = Pattern.compile(createdPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.created(parseDateTime(matcher.group(1)));
        }

        // PRIORITY
        String priorityPattern = "PRIORITY:(\\d+)";
        pattern = Pattern.compile(priorityPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.priority(Integer.parseInt(matcher.group(1)));
        }

        // BUSYSTATUS
        String busyStatusPattern = "X-MICROSOFT-CDO-BUSYSTATUS:(\\w+)";
        pattern = Pattern.compile(busyStatusPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.busyStatus(matcher.group(1));
        }

        // DTSTART
        String dtstartPattern = "DTSTART;TZID=HostOS;VALUE=DATE:(\\d{8})";
        pattern = Pattern.compile(dtstartPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.dtstart(parseDateTime(matcher.group(1)));
        }

        // DTEND
        String dtendPattern = "DTEND;TZID=HostOS;VALUE=DATE:(\\d{8})";
        pattern = Pattern.compile(dtendPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            builder.dtend(parseDateTime(matcher.group(1)));
        }

        // ATTENDEE
        String attendeePattern = "ATTENDEE;PARTSTAT=NEEDS-ACTION;ROLE=REQ-PARTICIPANT;RSVP=true;CN=(.+?):";
        pattern = Pattern.compile(attendeePattern);
        matcher = pattern.matcher(text);
        while (matcher.find()) {
            Attendee attendee = new Attendee();
            attendee.setCommonName(matcher.group(1));
            attendees.add(attendee);
        }
        builder.attendees(attendees);

        return builder.build();
    }

    private static LocalDateTime parseDateTime(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
            return LocalDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return LocalDate.parse(dateTime, formatter).atStartOfDay();
        }
    }

    private VEventParser() {
        throw new IllegalStateException("Utility class");
    }

}

