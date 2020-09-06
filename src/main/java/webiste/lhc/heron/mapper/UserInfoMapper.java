package webiste.lhc.heron.mapper;

import webiste.lhc.heron.config.mapperconfig.BaseMapper;
import webiste.lhc.heron.model.UserInfo;

import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    List<String> listPermissionByUserId(long id);
}
