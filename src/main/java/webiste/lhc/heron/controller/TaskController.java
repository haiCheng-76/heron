package webiste.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.dto.TaskDto;
import webiste.lhc.heron.model.Task;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.TaskService;
import webiste.lhc.heron.util.Resp;

@Controller()
@RequestMapping(value = "task")
public class TaskController extends AbstractController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MenuService menuService;


    /**
     * 分月获取任务列表
     *
     * @param offset
     * @param size
     * @return
     */
    @RequiresPermissions(value = "sys:task:list")
    @ResponseBody
    @GetMapping(value = "pageTask")
    public PageInfo<Task> pageTask(@RequestParam(value = "offset", required = false, defaultValue = "1") int offset, @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return taskService.listTasks(offset, size);
    }

    /**
     * 添加任务
     *
     * @param dto
     * @return
     */
    @RequiresPermissions(value = "sys:task:add")
    @ResponseBody
    @PostMapping(value = "addTask")
    public Resp addTask(@RequestBody TaskDto dto) {
        taskService.addTask(dto);
        return Resp.ok();
    }

    /**
     * 删除任务
     *
     * @param id
     * @return
     */
    @RequiresPermissions(value = "sys:task:del")
    @ResponseBody
    @GetMapping(value = "delTask")
    public Resp delTask(@RequestParam(value = "taskId") long id) {
        taskService.removeTask(id);
        return Resp.ok();
    }

    /**
     * 跳转定时任务页面
     *
     * @param name
     * @return
     */
    @RequiresPermissions(value = "sys:task:view")
    @GetMapping(value = "taskPage")
    public ModelAndView taskPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task/taskList");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }

    @RequiresPermissions(value = "sys:task:add")
    @GetMapping(value = "addTask")
    public ModelAndView addMenu() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task/addTask");
        return modelAndView;
    }

    @RequiresPermissions(value = "sys:task:list")
    @GetMapping(value = "taskDetail")
    public ModelAndView taskDetail(@RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task/taskDetail");
        modelAndView.addObject("task", taskService.getTaskById(id));
        return modelAndView;
    }

//    @RequiresPermissions(value = "sys:task:alter")
//    @PostMapping(value = "updateTask")
//    public ModelAndView updateTask(@RequestBody TaskDto dto) {
//        return modelAndView;
//    }
}
