package website.lhc.heron.service;

import com.github.pagehelper.PageInfo;
import website.lhc.heron.model.Logs;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/12/2 上午 01:03
 */
public interface LogsService {
    PageInfo<Logs> pageLogs(int current, int size);
}
