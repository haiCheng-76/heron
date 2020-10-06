package webiste.lhc.heron.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import oshi.util.FormatUtil;
import webiste.lhc.heron.model.SystemMonitor;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.SystemMonitorService;
import webiste.lhc.heron.util.Resp;
import webiste.lhc.heron.vo.SystemMonitorVo;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "monitor")
public class SystemMonitorController extends AbstractController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private SystemMonitorService systemMonitorService;

    /**
     * 跳转系统概况界面
     *
     * @param name
     * @return
     */
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

    /**
     * 跳转内存信息页面
     *
     * @param name
     * @return
     */
    @RequiresPermissions(value = "sys:memory:view")
    @GetMapping(value = "memoryPage")
    public ModelAndView memoryPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("monitor/memory");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }


    /**
     * 跳转虚拟机信息页面
     *
     * @param name
     * @return
     */
    @RequiresPermissions(value = "sys:vm:view")
    @GetMapping(value = "vmPage")
    public ModelAndView vmPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        modelAndView.setViewName("monitor/vm");
        return modelAndView;
    }

    @RequiresPermissions(value = {"sys:monitor:list", "sys:memory:view"}, logical = Logical.OR)
    @ResponseBody
    @GetMapping(value = "getChartsData")
    public Resp getChartsData(@RequestParam(value = "type") int type) {
        // 通过type判断需要读取的数据
        List<Map<String, Object>> longList = systemMonitorService.getMemoryData(type);
        return Resp.ok(longList);
    }

    /**
     * 内存占用饼图
     *
     * @param type
     * @return
     */
    @RequiresPermissions(value = {"sys:monitor:list", "sys:memory:view"}, logical = Logical.OR)
    @ResponseBody
    @GetMapping(value = "getMemoryInfo")
    public Resp getMemoryInfo(@RequestParam(value = "type") int type) {
        // 通过type判断需要读取的数据
        List<Long> longList = systemMonitorService.getMemoryInfo(type);
        return Resp.ok(longList);
    }
}
