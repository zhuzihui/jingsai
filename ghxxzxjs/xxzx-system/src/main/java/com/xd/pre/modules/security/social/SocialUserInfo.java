package com.xd.pre.modules.security.social;

import lombok.Data;

/**
 * @Classname SocialUserInfo
 * @Description
 */
@Data
public class SocialUserInfo {

    private String providerId;
    private String providerUserId;
    private String nickname;
    private String headImg;
}
