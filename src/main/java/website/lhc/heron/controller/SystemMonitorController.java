package website.lhc.heron.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import website.lhc.heron.model.SystemMonitor;
import website.lhc.heron.service.MenuService;
import website.lhc.heron.service.SystemMonitorService;
import website.lhc.heron.util.Resp;
import website.lhc.heron.vo.SystemMonitorVo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(tags = "系统监控")
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
    @ApiOperation(value = "跳转系统概况界面")
    @RequiresPermissions(value = "sys:monitor:view")
    @GetMapping(value = "monitorPage")
    public ModelAndView monitorPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("monitor/systemInfo");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        SystemMonitor systemMonitor = systemMonitorService.getLastSystemMonitor();
        if (Objects.nonNull(systemMonitor)) {
            SystemMonitorVo systemMonitorVo = new SystemMonitorVo();
            BeanUtils.copyProperties(systemMonitor, systemMonitorVo);
            systemMonitorVo.setMemoryTotalStr(FormatUtil.formatBytes(systemMonitor.getMemoryTotal()));
            systemMonitorVo.setMemoryUsedStr(FormatUtil.formatBytes(systemMonitor.getMemoryUsed()));
            systemMonitorVo.setMemoryAvailableStr(FormatUtil.formatBytes(systemMonitor.getMemoryAvailable()));
            systemMonitorVo.setJvmMemoryTotalStr(FormatUtil.formatBytes(systemMonitor.getJvmMemoryTotal()));
            systemMonitorVo.setJvmMemoryUsedStr(FormatUtil.formatBytes(systemMonitor.getJvmMemoryUsed()));
            systemMonitorVo.setJvmMemoryAvaliableStr(FormatUtil.formatBytes(systemMonitor.getJvmMemoryAvaliable()));
            modelAndView.addObject("systemMonitor", systemMonitorVo);
        } else {
            modelAndView.addObject("systemMonitor", new SystemMonitorVo());
        }

        return modelAndView;
    }

    /**
     * 跳转内存信息页面
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "跳转内存信息页面")
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
    @ApiOperation(value = "跳转虚拟机信息页面")
    @RequiresPermissions(value = "sys:vm:view")
    @GetMapping(value = "vmPage")
    public ModelAndView vmPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        modelAndView.setViewName("monitor/vm");
        return modelAndView;
    }

    @ApiOperation(value = "获取当前设备的内存波动信息")
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
    @ApiOperation(value = "内存占用饼图数据")
    @RequiresPermissions(value = {"sys:monitor:list", "sys:memory:view"}, logical = Logical.OR)
    @ResponseBody
    @GetMapping(value = "getMemoryInfo")
    public Resp getMemoryInfo(@RequestParam(value = "type") int type) {
        // 通过type判断需要读取的数据
        List<Long> longList = systemMonitorService.getMemoryInfo(type);
        return Resp.ok(longList);
    }
}
