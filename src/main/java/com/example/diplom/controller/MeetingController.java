package com.example.diplom.controller;

import com.example.diplom.dto.MeetingDTO;
import com.example.diplom.dto.MeetingsForm;
import com.example.diplom.service.MeetingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {


    private final MeetingService meetingService;

    private static final String MEETINGS = "meetings";

    public static final String MEETINGS_FORM = "meetingsForm";

    public static final String REDIRECT_MEETING_ALL = "redirect:/meeting/all";


    @GetMapping("/all")
    public String rulesPage(Model model) {
        model.addAttribute(MEETINGS_FORM, MeetingsForm.builder().meetings(meetingService.getAllMeetings()).build());
        return MEETINGS;
    }

    @PostMapping("/new")
    public String newMeeting(@ModelAttribute(MEETINGS_FORM) MeetingsForm meetingsForm) {
        meetingService.createMeeting();
        meetingsForm.setMeetings(meetingService.getAllMeetings());
        return REDIRECT_MEETING_ALL;
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute(MEETINGS_FORM) MeetingsForm meetingsForm) {
        meetingService.deleteMeetings(meetingsForm.getMeetings().stream().filter(MeetingDTO::isSelected).toList());
        meetingsForm.getMeetings().removeAll(meetingsForm.getMeetings().stream().filter(MeetingDTO::isSelected).toList());
        return REDIRECT_MEETING_ALL;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(MEETINGS_FORM) MeetingsForm meetingsForm) {
        meetingService.updateMeetings(meetingsForm.getMeetings());
        return REDIRECT_MEETING_ALL;
    }

    @PostMapping("/addAttendee/{itemUid}")
    public String addAttendee(@PathVariable("itemUid") String itemUid, @ModelAttribute(MEETINGS_FORM) MeetingsForm meetingsForm) {
        meetingsForm.getMeetings().stream()
            .filter(el -> el.getItemUid().equals(itemUid))
            .forEach(el -> el.getAttendees().add(""));
        return MEETINGS;
    }

    @PostMapping("/removeAttendee/{itemUid}")
    public String removeAttendee(@PathVariable("itemUid") String itemUid, @ModelAttribute(MEETINGS_FORM) MeetingsForm meetingsForm) {
        meetingsForm.getMeetings().stream()
            .filter(el -> el.getItemUid().equals(itemUid))
            .forEach(el -> el.getAttendees().remove(el.getAttendees().size() - 1));
        return MEETINGS;
    }


}
