package com.xd.pre.modules.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "pre.security")
public class PreSecurityProperties {

    private PreSocialProperties social = new PreSocialProperties();

}

