package webiste.lhc.heron.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webiste.lhc.heron.mapper.TaskMapper;
import webiste.lhc.heron.service.TaskService;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/28 下午 10:53
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
}
