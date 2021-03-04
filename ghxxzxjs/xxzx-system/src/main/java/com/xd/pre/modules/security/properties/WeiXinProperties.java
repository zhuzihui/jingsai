package com.xd.pre.modules.security.properties;

import lombok.Data;

/**
 * @Classname GithubProperties
 * @Description github第三方登录配置
 */
@Data
public class WeiXinProperties extends SocialProperties {

    private String providerId = "weixin";
}
