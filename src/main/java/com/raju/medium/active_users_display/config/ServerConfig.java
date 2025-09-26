package com.raju.medium.active_users_display.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.server")
public class ServerConfig {

    /**
     * Maximum number of users per server instance
     */
    private long maxUsers;

    /**
     * Warning threshold when approaching capacity (percentage of maxUsers)
     */
    private long warningThreshold;

    /**
     * Enable load balancing features
     */
    private boolean enableLoadBalancing;
}
