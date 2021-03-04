package com.xd.pre.modules.security.social.gitee.connect;

import com.xd.pre.modules.security.social.gitee.api.Gitee;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @Classname GiteeAdapter
 * @Description
 */
public class GiteeConnectionFactory extends OAuth2ConnectionFactory<Gitee> {

	public GiteeConnectionFactory(String providerId, String clientId, String clientSecret) {
		super(providerId, new GiteeServiceProvider(clientId, clientSecret), new GiteeAdapter());
	}
}