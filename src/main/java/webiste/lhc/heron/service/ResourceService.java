package webiste.lhc.heron.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ResourceService {
    Map<String, Object> saveResource(MultipartFile file);
}
