package com.xd.pre.modules.security.properties;


import lombok.Data;

/**
 * @Classname QQProperties
 * @Description QQ第三方登录配置
 */
@Data
public class QQProperties extends SocialProperties{

    private String providerId = "qq";
}
