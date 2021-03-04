package com.xd.pre.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xd.pre.modules.sys.domain.SysSocial;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 */
public interface SysSocialMapper extends BaseMapper<SysSocial> {

    /**
     * 获取rank的最大值加1
     * @param userId
     * @param providerId
     * @return
     */
    @Select("select coalesce(max(`rank`) + 1, 1) as `rank` from social_UserConnection where userId = #{userId} and providerId = #{providerId}")
    int getRank(String userId, String providerId);


}
