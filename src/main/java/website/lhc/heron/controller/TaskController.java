package website.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.lhc.heron.dto.TaskDto;
import website.lhc.heron.model.Task;
import website.lhc.heron.service.MenuService;
import website.lhc.heron.service.TaskService;
import website.lhc.heron.util.Assert;
import website.lhc.heron.util.Resp;

@Api(tags = "定时任务")
@Controller
@RequestMapping(value = "task")
public class TaskController extends AbstractController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MenuService menuService;


    /**
     * 分页获取任务列表
     *
     * @param offset
     * @param size
     * @return
     */
    @ApiOperation(value = "分页获取任务列表")
    @RequiresPermissions(value = "sys:task:list")
    @ResponseBody
    @GetMapping(value = "pageTask")
    public PageInfo<Task> pageTask(@RequestParam(value = "offset", required = false) int offset, @RequestParam(value = "limit", required = false) int size) {
        return taskService.listTasks(offset, size);
    }

    /**
     * 添加任务
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "添加任务")
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
    @ApiOperation(value = "删除任务")
    @RequiresPermissions(value = "sys:task:del")
    @ResponseBody
    @GetMapping(value = "delTask")
    public Resp delTask(@RequestParam(value = "id") long id) {
        taskService.removeTask(id);
        return Resp.ok();
    }

    /**
     * 跳转定时任务页面
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "跳转定时任务页面")
    @RequiresPermissions(value = "sys:task:view")
    @GetMapping(value = "taskPage")
    public ModelAndView taskPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task/taskList");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }

    /**
     * 跳转添加界面
     *
     * @return
     */
    @ApiOperation(value = "跳转定时任务添加界面")
    @RequiresPermissions(value = "sys:task:add")
    @GetMapping(value = "addTask")
    public ModelAndView addMenu() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task/addTask");
        return modelAndView;
    }

    /**
     * 跳转详细界面
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "跳转定时任务详细界面")
    @RequiresPermissions(value = "sys:task:list")
    @GetMapping(value = "taskDetail")
    public ModelAndView taskDetail(@RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task/taskDetail");
        modelAndView.addObject("task", taskService.getTaskById(id));
        return modelAndView;
    }

    /**
     * 跳转修改界面
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "跳转定时任务修改界面")
    @RequiresPermissions(value = "sys:task:alter")
    @GetMapping(value = "updateTask")
    public ModelAndView updateTask(@RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task/updateTask");
        modelAndView.addObject("task", taskService.getTaskById(id));
        return modelAndView;
    }


    /**
     * 修改定时任务
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "修改定时任务")
    @ResponseBody
    @RequiresPermissions(value = "sys:task:alter")
    @PostMapping(value = "updateTask")
    public Resp updateTask(@RequestBody TaskDto dto) {
        Assert.objectNotNull(dto.getId(), "id不可为空");
        taskService.updateTask(dto);
        return Resp.ok();
    }


    /**
     * 暂停任务
     *
     * @return
     */
    @ApiOperation(value = "暂停任务")
    @ResponseBody
    @RequiresPermissions(value = "sys:task:pause")
    @GetMapping(value = "pauseTask")
    public Resp pauseTask(@RequestParam(value = "id") long id) {
        taskService.pauseTask(id);
        return Resp.ok();
    }


    /**
     * 恢复任务
     *
     * @return
     */
    @ApiOperation(value = "恢复任务")
    @ResponseBody
    @RequiresPermissions(value = "sys:task:resume")
    @GetMapping(value = "resumeTask")
    public Resp resumeTask(@RequestParam(value = "id") long id) {
        taskService.resumeTask(id);
        return Resp.ok();
    }

}
