package com.ogel.common.rest.versioning;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for api versioning. Regular api versions 1, 2 etc.
 * For greater than version specification use '+' e.g 1+, 2+
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {
    String[] value();
}