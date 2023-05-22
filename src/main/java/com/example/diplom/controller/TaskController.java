package com.example.diplom.controller;

import com.example.diplom.dto.TaskDTO;
import com.example.diplom.dto.TasksForm;
import com.example.diplom.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/task")
public class TaskController {


    private final TaskService taskService;

    private static final String TASKS = "tasks";

    public static final String TASKS_FORM = "tasksForm";

    public static final String REDIRECT_TASK_ALL = "redirect:/task/all";


    @GetMapping("/all")
    private String rulesPage(Model model) {
        model.addAttribute(TASKS_FORM, TasksForm.builder().tasks(taskService.getAllTasks()).build());
        return TASKS;
    }

    @GetMapping("/new")
    private String newRule() {
        return TASKS;
    }


    @PostMapping("/all")
    public String processTaskForm(@ModelAttribute(TASKS_FORM) TasksForm tasksForm, @RequestParam("action") String action) {
        switch (action) {
            case "save":
                taskService.updateTasks(tasksForm.getTasks());
                break;
            case "delete":
                taskService.deleteTasks(tasksForm.getTasks().stream().filter(TaskDTO::isSelected).toList());
                tasksForm.getTasks().removeAll(tasksForm.getTasks().stream().filter(TaskDTO::isSelected).toList());
                break;
            case "new":
                taskService.createTask();
                tasksForm.setTasks(taskService.getAllTasks());
                break;
        }
        return REDIRECT_TASK_ALL;
    }

    @PostMapping("/done/{uid}")
    public String doneTask(@PathVariable("uid") Long uid, @ModelAttribute(TASKS_FORM) TasksForm tasksForm) {
        taskService.updateTask(
            tasksForm.getTasks().stream()
                .filter(el -> el.getUid().equals(uid))
                .peek(el -> el.setPercentComplete(100))
                .peek(el -> {
                    if (el.getTaskText() == null || el.getTaskText().isEmpty())
                        el.setTaskText(taskService.getTaskByUid(uid).getTaskText());
                })
                .findAny()
                .orElse(taskService.getTaskByUid(uid)));
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
