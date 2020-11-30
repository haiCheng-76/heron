package website.lhc.heron.service;

import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import website.lhc.heron.model.Resource;

import java.util.List;
import java.util.Map;

public interface ResourceService {
    Map<String, Object> saveResource(MultipartFile file);

    List<String> listBucketName();
    List<String> listResoureType();

    List<Resource> getResources(String bucket);

    PageInfo<Resource> pageResource(int current, int size, String type);

    void delResources(List<Integer> ids);
}
