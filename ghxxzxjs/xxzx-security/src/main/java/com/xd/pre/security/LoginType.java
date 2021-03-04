package com.xd.pre.security;


import lombok.Getter;

/**
 * @Classname PreAuthenticationSuccessHandler
 * @Description 登录类型 现在有用户名 短信 社交
 */
@Getter
public enum LoginType {
    normal, sms, social;
}
