package website.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import website.lhc.heron.model.Logs;
import website.lhc.heron.service.LogsService;
import website.lhc.heron.service.MenuService;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/12/2 上午 01:07
 */
@Controller
@RequestMapping(value = "/logs")
public class LogsController extends AbstractController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private LogsService logsService;

    @ApiOperation(value = "跳转登录日志页面")
    @RequiresPermissions(value = "sys:logs:list")
    @GetMapping(value = "/logsPage")
    public ModelAndView resourcePage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("logs");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }

    @ResponseBody
    @GetMapping(value = "/pageInfo")
    public PageInfo<Logs> pageInfo(@RequestParam(value = "offset") int current,
                                   @RequestParam(value = "limit") int size) {
        return logsService.pageLogs(current, size);
    }
}
