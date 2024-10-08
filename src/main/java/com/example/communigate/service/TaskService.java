package com.example.communigate.service;

import com.example.communigate.dictionary.FolderName;
import com.example.communigate.dto.TaskDTO;
import com.example.communigate.manager.XimssService;
import com.example.communigate.ximss.request.*;
import com.example.communigate.ximss.response.CalendarItem;
import com.example.communigate.ximss.response.Tasks;
import com.example.communigate.ximss.response_request.VTodo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final XimssService ximssService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public List<TaskDTO> getAllTasks() {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.TASKS.getValue()).build());
        final List<Tasks> tasks = ximssService.sendRequestToGetList(FindTasks.builder()
                .calendar(FolderName.TASKS.getValue())
                .completed("yes")
                .timeFrom(LocalDate.now().minusDays(7).format(formatter))
                .timeTill(LocalDate.now().plusDays(7).format(formatter))
                .build(), Tasks.class);

        final List<CalendarItem> calendarItems = tasks.stream()
                .filter(el -> el.getTaskList() != null)
                .flatMap(tasks1 -> tasks1.getTaskList().stream())
                .map(Tasks.Task::getUid)
                .distinct()
                .map(uid -> ximssService.sendRequestToGetObject(CalendarReadItem.builder().calendar(FolderName.TASKS.getValue()).uid(uid).build(), CalendarItem.class))
                .toList();
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.TASKS.getValue()).build());
        return calendarItems.stream().map(TaskDTO::new)
                .sorted(Comparator.comparing(TaskDTO::getPercentComplete).thenComparing(Comparator.comparing(TaskDTO::getTimeCreated).reversed()))
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public TaskDTO getTaskByUid(final Long uid) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.TASKS.getValue()).build());
        final CalendarItem calendarItem = ximssService.sendRequestToGetObject(CalendarReadItem.builder().calendar(FolderName.TASKS.getValue()).uid(uid).build(), CalendarItem.class);
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.TASKS.getValue()).build());
        return new TaskDTO(calendarItem);
    }

    public VTodo getVtodoByUid(final Long uid) {
        final CalendarItem calendarItem = ximssService.sendRequestToGetObject(CalendarReadItem.builder().calendar(FolderName.TASKS.getValue()).uid(uid).build(), CalendarItem.class);
        return calendarItem.getVTodo();
    }


    public void updateTasks(final List<TaskDTO> tasks) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.TASKS.getValue()).build());
        tasks.forEach(this::updateTaskByTaskDto);
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.TASKS.getValue()).build());
    }

    public void updateTask(final TaskDTO taskDTO) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.TASKS.getValue()).build());
        updateTaskByTaskDto(taskDTO);
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.TASKS.getValue()).build());
    }

    private void updateTaskByTaskDto(TaskDTO taskDTO) {
        final VTodo vtodo = getVtodoByUid(taskDTO.getUid());
        vtodo.setSummary(taskDTO.getTaskText());
        vtodo.setPercentComplete(taskDTO.getPercentComplete());
        vtodo.setDtstart(taskDTO.getTimeStart());
        vtodo.setDue(taskDTO.getTimeEnd());
        ximssService.sendRequestToGetNothing(
                CalendarPublish.builder()
                        .calendar(FolderName.TASKS.getValue())
                        .iCalendar(new ICalendar(VCalendar.builder().vTodo(vtodo).build()))
                        .build());
    }

    public void deleteTasks(final List<TaskDTO> tasks) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.TASKS.getValue()).build());
        tasks.forEach(taskDTO -> ximssService.sendRequestToGetNothing(
                CalendarCancel.builder()
                        .calendar(FolderName.TASKS.getValue())
                        .itemUid(taskDTO.getTaskUid())
                        .build()));
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.TASKS.getValue()).build());
    }

    public void createTask() {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(FolderName.TASKS.getValue()).build());
        ximssService.sendRequestToGetNothing(
                CalendarPublish.builder()
                        .calendar(FolderName.TASKS.getValue())
                        .iCalendar(new ICalendar(VCalendar.builder().vTodo(VTodo.builder().build()).build()))
                        .build());
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(FolderName.TASKS.getValue()).build());
    }
}
