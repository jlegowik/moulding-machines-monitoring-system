package com.ogel.common.actuator;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;

public class VersionInfoContributor implements InfoContributor {

    private final String version;

    public VersionInfoContributor(Object caller) {
        version = caller.getClass().getPackage().getImplementationVersion();
    }

    @Override
    public void contribute(Builder builder) {
        builder.withDetail("version", version);
    }

}
