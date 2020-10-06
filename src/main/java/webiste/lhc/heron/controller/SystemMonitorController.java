package webiste.lhc.heron.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import oshi.util.FormatUtil;
import webiste.lhc.heron.model.SystemMonitor;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.SystemMonitorService;
import webiste.lhc.heron.vo.SystemMonitorVo;

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
        SystemMonitorVo systemMonitorVo = new SystemMonitorVo();
        BeanUtils.copyProperties(systemMonitor, systemMonitorVo);
        systemMonitorVo.setMemoryTotalStr(FormatUtil.formatBytes(systemMonitor.getMemoryTotal()));
        systemMonitorVo.setMemoryUsedStr(FormatUtil.formatBytes(systemMonitor.getMemoryUsed()));
        systemMonitorVo.setMemoryAvailableStr(FormatUtil.formatBytes(systemMonitor.getMemoryAvailable()));
        systemMonitorVo.setJvmMemoryTotalStr(FormatUtil.formatBytes(systemMonitor.getJvmMemoryTotal()));
        systemMonitorVo.setJvmMemoryUsedStr(FormatUtil.formatBytes(systemMonitor.getJvmMemoryUsed()));
        systemMonitorVo.setJvmMemoryAvaliableStr(FormatUtil.formatBytes(systemMonitor.getJvmMemoryAvaliable()));
        modelAndView.addObject("systemMonitor", systemMonitorVo);
        return modelAndView;
    }
}
