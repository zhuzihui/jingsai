package com.xd.pre.modules.security.social.github.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Classname GiteeAdapter
 * @Description github 用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitHubUserInfo {
    private long id;
    private String name;
    private String username;
    private String location;
    private String company;
    private String blog;
    private String email;
    private Date createdDate;
    private String profileImageUrl;
    private String avatarUrl;
}
