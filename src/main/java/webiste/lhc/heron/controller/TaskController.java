package webiste.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webiste.lhc.heron.dto.TaskDto;
import webiste.lhc.heron.model.Task;
import webiste.lhc.heron.service.TaskService;
import webiste.lhc.heron.util.Resp;

@Controller()
@RequestMapping(value = "task")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @RequiresGuest
    @ResponseBody
    @GetMapping(value = "pageTask")
    public Resp pageTask(@RequestParam(value = "offset", required = false, defaultValue = "1") int offset, @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PageInfo<Task> taskPageInfo = taskService.listTasks(offset, size);
        return Resp.ok(taskPageInfo);
    }

    @RequiresGuest
    @ResponseBody
    @PostMapping(value = "addTask")
    public Resp addTask(@RequestBody TaskDto dto) {
        taskService.addTask(dto);
        return Resp.ok();
    }

    @ResponseBody
    @GetMapping(value = "delTask")
    public Resp delTask(@RequestParam(value = "taskId") long id) {
        taskService.removeTask(id);
        return Resp.ok();
    }
}
