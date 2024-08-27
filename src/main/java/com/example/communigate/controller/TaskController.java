package com.example.communigate.controller;

import com.example.communigate.dto.TaskDTO;
import com.example.communigate.dto.TasksForm;
import com.example.communigate.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {


    private final TaskService taskService;

    private static final String TASKS = "tasks";

    public static final String TASKS_FORM = "tasksForm";

    public static final String REDIRECT_TASK_ALL = "redirect:/task/all";


    @GetMapping("/all")
    public String rulesPage(Model model) {
        model.addAttribute(TASKS_FORM, TasksForm.builder().tasks(taskService.getAllTasks()).build());
        return TASKS;
    }

    @PostMapping("/all")
    public String processTaskForm(@ModelAttribute(TASKS_FORM) TasksForm tasksForm, @RequestParam("action") String action) {
        switch (action) {
            case "save" -> taskService.updateTasks(tasksForm.getTasks());
            case "delete" -> {
                taskService.deleteTasks(tasksForm.getTasks().stream().filter(TaskDTO::isSelected).toList());
                tasksForm.getTasks().removeAll(tasksForm.getTasks().stream().filter(TaskDTO::isSelected).toList());
            }
            case "new" -> {
                taskService.createTask();
                tasksForm.setTasks(taskService.getAllTasks());
            }
            default -> log.warn("You are don't define this action yet");
        }
        return REDIRECT_TASK_ALL;
    }

    @PostMapping("/done/{uid}")
    public String doneTask(@PathVariable("uid") Long uid, @ModelAttribute(TASKS_FORM) TasksForm tasksForm) {
        TaskDTO taskDTO = tasksForm.getTasks().stream().filter(el -> el.getUid().equals(uid)).findAny().orElse(taskService.getTaskByUid(uid));
        taskDTO.setPercentComplete(100);
        if (taskDTO.getTaskText() == null || taskDTO.getTaskText().isEmpty()) {
            taskDTO.setTaskText(taskService.getTaskByUid(uid).getTaskText());
        }
        taskService.updateTask(taskDTO);
        return REDIRECT_TASK_ALL;
    }

    @PostMapping("/undone/{uid}")
    public String undoneTask(@PathVariable("uid") Long uid) {
        TaskDTO taskByUid = taskService.getTaskByUid(uid);
        taskByUid.setPercentComplete(0);
        taskService.updateTasks(List.of(taskByUid));
        return REDIRECT_TASK_ALL;
    }

}
