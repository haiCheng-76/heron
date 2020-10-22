package webiste.lhc.heron.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.ResourceService;

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
        modelAndView.addObject("buckets", resourceService.listBucketName());
        modelAndView.addObject("types", resourceService.listResoureType());
        return modelAndView;
    }
}
