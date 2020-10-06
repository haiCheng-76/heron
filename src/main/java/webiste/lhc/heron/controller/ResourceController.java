package webiste.lhc.heron.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import webiste.lhc.heron.service.ResourceService;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ResponseBody
    @PostMapping(value = "uploadResource")
    public Map<String, Object> uploadResource(@RequestParam(value = "file") MultipartFile file) {
        return resourceService.saveResource(file);
    }
}
