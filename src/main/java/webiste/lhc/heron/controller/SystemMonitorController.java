package webiste.lhc.heron.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.model.SystemMonitor;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.SystemMonitorService;

@Controller
@RequestMapping(value = "monitor")
public class SystemMonitorController extends AbstractController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private SystemMonitorService systemMonitorService;

    @RequiresPermissions(value = "sys:monitor:view")
    @GetMapping(value = "monitorPage")
    public ModelAndView monitorPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("monitor/systemInfo");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        SystemMonitor systemMonitor = systemMonitorService.getLastSystemMonitor();
        modelAndView.addObject("systemMonitor", systemMonitor);
        return modelAndView;
    }
}
