package com.xd.pre.generator.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname SysDatasource
 * @Description TODO
 */
@Data
public class SysDatasource {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * jdbcurl
     */
    private String url;

    /**
     * 驱动
     */
    private String driverName;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private LocalDateTime createDate;
    /**
     * 更新
     */
    private LocalDateTime updateDate;
}
