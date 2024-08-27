package com.example.communigate.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TasksForm {

    @Builder.Default
    private List<TaskDTO> tasks = new ArrayList<>();

}
