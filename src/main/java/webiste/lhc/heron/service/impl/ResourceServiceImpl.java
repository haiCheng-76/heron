package webiste.lhc.heron.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import webiste.lhc.heron.framework.minio.MinIoService;
import webiste.lhc.heron.mapper.ResourceMapper;
import webiste.lhc.heron.model.Resource;
import webiste.lhc.heron.service.ResourceService;
import webiste.lhc.heron.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {


    @Autowired
    private ResourceMapper resourceMapper;


    @Autowired
    private MinIoService minIoService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> saveResource(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        if (file == null || file.isEmpty()) {
            map.put("errno", 1);
            map.put("message", "文件为空，请重新上传！");
            return map;
        }
        Assert.stat(file.isEmpty(), "文件为空，请选择文件");
        String fileUrl = null;
        long fileSize = file.getSize();
        String path = file.getOriginalFilename();
        String contentType = file.getContentType();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.warn("获取文件流异常;IOException:{}", e.getMessage());
            e.printStackTrace();
            map.put("errno", 1);
            map.put("message", "系统错误，请重新上传！");
            return map;
        }
        String bucketName = DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        String newFileName = DateUtil.format(LocalDateTime.now(), "yyyyMMddHHmmssSSS") + "." + StringUtils.split(path, ".")[1];
        try {
            fileUrl = minIoService.uploadFile(bucketName, newFileName, inputStream, contentType, fileSize);
        } catch (Exception e) {
            log.warn("MinIo文件上传异常;Exception:{}", e.getMessage());
            e.printStackTrace();
            map.put("errno", 1);
            map.put("message", "对象存储服务异常，请稍后再试！");
            return map;
        }
        log.info("fileSize:{};path:{};contentType:{};bucketName:{};newFileName:{}", fileSize, path, contentType, bucketName, newFileName);
        Resource resource = new Resource();
        resource.setBucketName(bucketName);
        resource.setCreateTime(new Date());
        resource.setResourceName(newFileName);
        resource.setSize(fileSize);
        resource.setResourceType(StringUtils.split(path, ".")[1]);
        resource.setResourcePath(fileUrl);
        resource.setCreateUser("admin");
        resourceMapper.insertUseGeneratedKeys(resource);
        List<String> list = new ArrayList<>(1);
        list.add(fileUrl);
        map.put("errno", 0);
        map.put("data", list);
        return map;
    }

    @Override
    public List<String> listBucketName() {
        return minIoService.bucketList();
    }

    @Override
    public List<String> listResoureType() {
        return resourceMapper.resourceType();
    }

    @Override
    public List<Resource> getResources(String bucket) {
        Example example = new Example(Resource.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("", bucket);
        example.orderBy("createTime").desc();
        return resourceMapper.selectByExample(example);
    }

    @Override
    public PageInfo<Resource> pageResource(int current, int size, String type) {
        PageHelper.offsetPage(current, size);
        Example example = new Example(Resource.class);
        if (StringUtils.hasLength(type)) {
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("resourceType", type);
        }
        example.orderBy("createTime").desc();
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        return new PageInfo<>(resourceList);
    }

}
