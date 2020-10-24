package webiste.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.model.Resource;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.ResourceService;
import webiste.lhc.heron.util.Resp;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "resource")
public class ResourceController extends AbstractController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MenuService menuService;


    @ResponseBody
    @PostMapping(value = "uploadResource")
    public Map<String, Object> uploadResource(@RequestParam(value = "file") MultipartFile file) {
        return resourceService.saveResource(file);
    }

    @RequiresPermissions(value = "sys:resource:list")
    @GetMapping(value = "/resourcePage")
    public ModelAndView resourcePage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("resource/resourceList");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        modelAndView.addObject("resourceTypes", resourceService.listResoureType());
        return modelAndView;
    }

    /**
     * 资源分页
     * @param current
     * @param size
     * @param type
     * @return
     */
    @ResponseBody
    @GetMapping(value = "resourcePageInfo")
    public PageInfo<Resource> resourcePageInfo(@RequestParam(value = "offset") int current,
                                               @RequestParam(value = "limit") int size,
                                               @RequestParam(value = "type", required = false) String type) {
        return resourceService.pageResource(current, size, type);
    }

    /**
     * 批量删除minio资源
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping(value = "batchDelResources")
    public Resp batchDelResources(@RequestBody List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Resp.error("数据为空，无法执行操作");
        }
        resourceService.delResources(ids);
        return Resp.ok();
    }
}
