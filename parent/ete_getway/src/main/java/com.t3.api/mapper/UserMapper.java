package com.t3.api.mapper;

import com.t3.api.entity.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2019/12/30.
 */
@Mapper
public interface UserMapper {

    public UserBean selectLoginUserByKeyAuth(@Param("key") String paramString1, @Param("authId") String paramString2);
}
