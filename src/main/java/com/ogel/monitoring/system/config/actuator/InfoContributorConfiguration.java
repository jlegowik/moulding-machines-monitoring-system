package com.ogel.monitoring.system.config.actuator;

import com.ogel.common.actuator.VersionInfoContributor;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfoContributorConfiguration {

    @Bean
    public InfoContributor versionInfoContributor() {
        return new VersionInfoContributor(this);
    }

}