package com.example.diplom.service;

import com.example.diplom.dto.TaskDTO;
import com.example.diplom.manager.XimssService;
import com.example.diplom.ximss.request.*;
import com.example.diplom.ximss.response.CalendarItem;
import com.example.diplom.ximss.response.Tasks;
import com.example.diplom.ximss.response_request.VTodo;
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

    private final UserCache userCache;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final String FILE_IM_1_PATH_PATTERN = "private/IM/%s@ivanov.ru.log";

    private static final String FILE_IM_2_PATH_PATTERN = "private/IM/%s@ivanov.log";

    private static final String FILE_IM_3_PATH_PATTERN = "private/IM/%s.log";

    public static final String TASKS = "Tasks";


    public ArrayList<TaskDTO> getAllTasks() {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(TASKS).build());
        final List<Tasks> tasks = ximssService.sendRequestToGetList(FindTasks.builder()
            .calendar(TASKS)
            .completed("yes")
            .timeFrom(LocalDate.now().minusDays(7).format(formatter))
            .timeTill(LocalDate.now().plusDays(7).format(formatter))
            .build(), Tasks.class);

        final List<CalendarItem> calendarItems = tasks.stream()
            .filter(el -> el.getTasks() != null)
            .flatMap((Tasks tasks1) -> tasks1.getTasks().stream())
            .map(Tasks.Task::getUid)
            .distinct()
            .map(uid -> ximssService.sendRequestToGetObject(CalendarReadItem.builder().calendar(TASKS).uid(uid).build(), CalendarItem.class))
            .toList();
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(TASKS).build());
        return calendarItems.stream().map(TaskDTO::new)
            .sorted(Comparator.comparing(TaskDTO::getPercentComplete))
            .collect(Collectors.toCollection(ArrayList::new));

    }

    public TaskDTO getTaskByUid(final Long uid) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(TASKS).build());
        final CalendarItem calendarItem = ximssService.sendRequestToGetObject(CalendarReadItem.builder().calendar(TASKS).uid(uid).build(), CalendarItem.class);
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(TASKS).build());
        return new TaskDTO(calendarItem);
    }

    public VTodo getVtodoByUid(final Long uid) {
        final CalendarItem calendarItem = ximssService.sendRequestToGetObject(CalendarReadItem.builder().calendar(TASKS).uid(uid).build(), CalendarItem.class);
        return calendarItem.getVTodo();
    }


    public void updateTasks(final List<TaskDTO> tasks) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(TASKS).build());
        tasks.forEach(this::updateTaskByTaskDto);
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(TASKS).build());
    }

    public void updateTask(final TaskDTO taskDTO) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(TASKS).build());
        updateTaskByTaskDto(taskDTO);
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(TASKS).build());
    }

    private void updateTaskByTaskDto(TaskDTO taskDTO) {
        final VTodo vtodo = getVtodoByUid(taskDTO.getUid());
        vtodo.setSummary(taskDTO.getTaskText());
        vtodo.setPercentComplete(taskDTO.getPercentComplete());
        vtodo.setDtstart(taskDTO.getTimeStart());
        vtodo.setDue(taskDTO.getTimeEnd());
        ximssService.sendRequestToGetNothing(
            CalendarPublish.builder()
                .calendar(TASKS)
                .iCalendar(new ICalendar(new VCalendar(vtodo)))
                .build());
    }

    public void deleteTasks(final List<TaskDTO> tasks) {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(TASKS).build());
        tasks.stream()
            .filter(TaskDTO::isSelected)
            .forEach(taskDTO -> ximssService.sendRequestToGetNothing(
                CalendarCancel.builder()
                    .calendar(TASKS)
                    .itemUid(taskDTO.getTaskUid())
                    .build()));
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(TASKS).build());
    }

    public void createTask() {
        ximssService.sendRequestToGetNothing(CalendarOpen.builder().calendar(TASKS).build());
        ximssService.sendRequestToGetNothing(
            CalendarPublish.builder()
                .calendar(TASKS)
                .iCalendar(new ICalendar(new VCalendar(VTodo.builder().build())))
                .build());
        ximssService.sendRequestToGetNothing(CalendarClose.builder().calendar(TASKS).build());
    }
}
