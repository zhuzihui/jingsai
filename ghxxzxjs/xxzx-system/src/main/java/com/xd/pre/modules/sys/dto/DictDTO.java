package com.xd.pre.modules.sys.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Classname DictDTO
 * @Description 字典dto
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictDTO {


    private Integer id;

    private String dictName;

    private String dictCode;

    private String description;

    private Integer sort;

    private String remark;
}
