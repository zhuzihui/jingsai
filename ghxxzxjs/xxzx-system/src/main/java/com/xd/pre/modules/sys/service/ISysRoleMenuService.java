package com.xd.pre.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.sys.domain.SysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    List<Integer> getMenuIdByUserId(Integer userId);


}
