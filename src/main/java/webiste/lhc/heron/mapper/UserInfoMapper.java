package webiste.lhc.heron.mapper;

import org.apache.ibatis.annotations.Param;
import webiste.lhc.heron.config.mapperconfig.BaseMapper;
import webiste.lhc.heron.model.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    void addUserRole(@Param(value = "roleId") long roleId,@Param(value = "userId") long userId);
}
