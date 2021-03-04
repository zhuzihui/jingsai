package com.xd.pre.modules.security.social;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname PreConnectionData
 * @Description 第三方数据类
 */
@Data
public class PreConnectionData implements Serializable {
    private String providerId;
    private String providerUserId;
    private String displayName;
    private String profileUrl;
    private String imageUrl;
    private String accessToken;
    private String secret;
    private String refreshToken;
    private Long expireTime;
}
