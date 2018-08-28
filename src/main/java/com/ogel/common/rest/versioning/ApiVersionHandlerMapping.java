package com.ogel.common.rest.versioning;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    private final String prefix;
    private final String[] availableVersions;
    private static final String GREATER_VERSION_SUFFIX = "+";

    public ApiVersionHandlerMapping(String prefix, String... availableVersions) {
        this.prefix = prefix;
        this.availableVersions = availableVersions;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info != null) {
            ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
            if (methodAnnotation != null) {
                RequestCondition<?> methodCondition = getCustomMethodCondition(method);
                info = createApiVersionInfo(methodAnnotation, methodCondition).combine(info);
            } else {
                ApiVersion typeAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
                if (typeAnnotation != null) {
                    RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
                    info = createApiVersionInfo(typeAnnotation, typeCondition).combine(info);
                }
            }
        }
        return info;
    }

    private RequestMappingInfo createApiVersionInfo(ApiVersion annotation, RequestCondition<?> customCondition) {
        return new RequestMappingInfo(
                new PatternsRequestCondition(getVersionList(annotation.value()).toArray(new String[0]), getUrlPathHelper(),
                        getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(), new ParamsRequestCondition(), new HeadersRequestCondition(),
                new ConsumesRequestCondition(), new ProducesRequestCondition(), customCondition);
    }

    private Set<String> getVersionList(String[] annotationValue) {
        List<String> valuesList = new ArrayList<>(Arrays.asList(annotationValue));
        Set<String> patterns = valuesList.stream().filter(x -> x.endsWith(GREATER_VERSION_SUFFIX))
                .flatMap(this::expandVersions).collect(Collectors.toSet());
        patterns.addAll(valuesList.stream().filter(x -> !x.endsWith(GREATER_VERSION_SUFFIX)).collect(Collectors.toSet()));
        return patterns.stream().map(x -> prefix + x).collect(Collectors.toSet());
    }

    private Stream<String> expandVersions(String version) {
        return resolveVersions(version.substring(0, version.length() - 1),
                Comparator.comparing(Double::valueOf)).stream();
    }

    private Set<String> resolveVersions(String version, Comparator<String> versionComparator) {
        return Arrays.stream(availableVersions).filter(v -> versionComparator.compare(v, version) >= 0)
                .collect(Collectors.toSet());
    }

}
