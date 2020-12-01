package website.lhc.heron.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import website.lhc.heron.mapper.LogsMapper;
import website.lhc.heron.model.Logs;
import website.lhc.heron.service.LogsService;

import java.util.List;

/**
 * @description: 登录日志
 * @author: 582895699@qq.com
 * @time: 2020/12/2 上午 01:04
 */
@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    private LogsMapper logsMapper;


    @Override
    public PageInfo<Logs> pageLogs(int current, int size) {
        PageHelper.offsetPage(current, size);
        Example example = new Example(Logs.class);
        example.orderBy("id").desc();
        List<Logs> logsList = logsMapper.selectByExample(example);
        return new PageInfo<>(logsList);
    }
}
