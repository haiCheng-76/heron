package website.lhc.heron.framework.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.config
 * @ClassName: BaseMapper
 * @Author: lhc
 * @Description: 通用Mapper接口类
 * @Date: 2020/8/16 上午 11:02
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
