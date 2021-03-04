package com.xd.pre.modules.security.social.weixin.api;


/**
 * @Classname Weixin
 * @Description 微信API调用接口
 */
public interface Weixin {

    WeixinUserInfo getUserInfo(String openId);
}
