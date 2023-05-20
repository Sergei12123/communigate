package com.example.diplom.dto;

import com.example.diplom.ximss.response.CalendarItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO {

    private String taskUid;

    private Long uid;

    private String taskText;

    @JsonIgnore
    private int percentComplete;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss")
    private LocalDateTime timeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss")
    private LocalDateTime timeEnd;

    @JsonIgnore
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

}
