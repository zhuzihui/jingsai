package com.xd.pre.modules.sys.dto;

import com.xd.pre.modules.sys.domain.SysRoleMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author zhb
 * @Classname UserDTO
 * @Description 角色Dto
 */
@Setter
@Getter
public class RoleDTO {

    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private String roleName;
    private String roleCode;
    private String roleDesc;
    private String delFlag;
    private int dsType;
    private int areAdmin;
    List<SysRoleMenu> roleMenus;
    List<Integer> roleDepts;



}
