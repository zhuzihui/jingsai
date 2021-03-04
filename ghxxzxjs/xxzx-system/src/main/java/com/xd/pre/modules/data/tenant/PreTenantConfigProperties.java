package com.xd.pre.modules.data.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname PreTenantConfigProperties
 * @Description 多租户动态配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "pre.tenant")
public class PreTenantConfigProperties {

    /**
     * 维护租户id
     */
    private String tenantIdColumn;

    /**
     * 多租户的数据表集合
     */
    private List<String> ignoreTenantTables = new ArrayList<>();
}
