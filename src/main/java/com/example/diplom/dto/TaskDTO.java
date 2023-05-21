package com.example.diplom.dto;

import com.example.diplom.ximss.response.CalendarItem;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private String taskUid;

    private Long uid;

    private String taskText;

    private int percentComplete;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timeEnd;

    private boolean selected;

    public TaskDTO(final CalendarItem calendarItem) {
        if (calendarItem.getVTodo() != null) {
            this.uid = calendarItem.getUid();
            this.taskUid = calendarItem.getVTodo().getUid();
            this.taskText = calendarItem.getVTodo().getSummary();
            this.timeStart = calendarItem.getVTodo().getDtstart();
            this.timeEnd = calendarItem.getVTodo().getDue();
            this.percentComplete = calendarItem.getVTodo().getPercentComplete();
        }
    }

    public TaskDTO(final String taskText) {
        this.taskText = taskText;
    }

}
