package website.lhc.heron.service;

import com.github.pagehelper.PageInfo;
import website.lhc.heron.dto.TaskDto;
import website.lhc.heron.model.Task;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/28 下午 10:53
 */
public interface TaskService {
    void addTask(TaskDto dto);

    void updateTask(TaskDto dto);

    PageInfo<Task> listTasks(int offset, int size);

    void initTasks();

    void removeTask(long id);

    Task getTaskById(long id);

    void pauseTask(long id);

    void resumeTask(long id);
}
