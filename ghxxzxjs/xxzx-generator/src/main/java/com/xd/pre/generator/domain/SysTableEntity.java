package com.xd.pre.generator.domain;

import lombok.Data;

/**
 * @Classname Table
 * @Description 数据库表属性
 *SELECT TABLE_NAME,TABLE_COMMENT,TABLE_SCHEMA,CREATE_TIME FROM information_schema.TABLES WHERE table_schema='pre';
 */
@Data
public class SysTableEntity {

    /**
     * 名称
     */
    private String tableName;
    /**
     * 备注
     */
    private String comments;
    /**
     * 归属库
     */
    private String tableSchema;
    /**
     * 创建时间
     */
    private String createTime;
}
