package com.example.diplom.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TasksForm {

    private List<TaskDTO> tasks;

}
