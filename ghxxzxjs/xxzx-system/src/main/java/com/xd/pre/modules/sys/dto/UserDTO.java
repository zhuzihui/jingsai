package com.xd.pre.modules.sys.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @auther:zlk
 * @date:2021-3-5
 * @description: 用户Dto
 */
@Data
public class UserDTO implements Serializable {

    private Integer userId;
    private String username;
    private String password;
    private Integer deptId;
    private String phone;
    private String email;
    private String avatar;
    private String lockFlag;
    private String delFlag;
    private List<Integer> roleList;
    private List<Integer> deptList;
    /**
     * 新密码
     */
    private String newPassword;
    private String smsCode;
    // 部门名称
    private String dept;
    // 权限名称
    private String role;
}
