package com.xd.pre.modules.sys.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Classname UserDTO
 * @Description 部门Dto
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptDTO {

    private static final long serialVersionUID = 1L;

    private Integer deptId;

    /**
     * 部门名称
     */
    private String name;


    /**
     * 上级部门
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;


}
