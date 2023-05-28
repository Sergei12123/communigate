package com.example.diplom.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingsForm {

    @Builder.Default
    private List<MeetingDTO> meetings = new ArrayList<>();

}
