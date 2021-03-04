package com.xd.pre.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xd.pre.modules.sys.domain.SysMenu;
import com.xd.pre.modules.sys.dto.MenuDTO;
import com.xd.pre.common.utils.R;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 更新菜单信息
     * @param entity
     * @return
     */
    boolean updateMenuById(MenuDTO entity);

    /**
     * 删除菜单信息
     * @param id
     * @return
     */
    R removeMenuById(Serializable id);

    /**
     * 根据用户id查找菜单树
     * @return
     */
    List<SysMenu> selectMenuTree(Integer uid);

    /**
     * @Description 根据父id查询菜单
     **/
    SysMenu getMenuById(Integer parentId);

    /**
     * @Description 根据用户id查询权限
     **/
    List<String> findPermsByUserId(Integer userId);
}
