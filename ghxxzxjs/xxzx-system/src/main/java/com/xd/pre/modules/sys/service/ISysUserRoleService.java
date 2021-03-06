package com.xd.pre.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.sys.domain.SysUserRole;

import java.util.List;

/**
 * 用户角色表 服务类
 * @auther:zlk
 * @date:2021-3-5
 * @description:
 *
 */
public interface ISysUserRoleService extends IService<SysUserRole> {


    /**
     * 根据用户id查询用户角色关系
     * @param userId
     * @return
     */
    List<SysUserRole> selectUserRoleListByUserId(Integer userId);
}
