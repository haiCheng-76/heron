package website.lhc.heron.mapper;

import org.apache.ibatis.annotations.Param;
import website.lhc.heron.framework.mybatis.BaseMapper;
import website.lhc.heron.model.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    void addUserRole(@Param(value = "roleId") long roleId,@Param(value = "userId") long userId);
}
