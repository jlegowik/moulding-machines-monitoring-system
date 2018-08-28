package com.ogel.common.correlation;

public abstract class CorrelationIdHolder {

    private static final ThreadLocal<String> id = new ThreadLocal<>();

    public static String get() {
        return id.get();
    }

    public static void set(String correlationId) {
        id.set(correlationId);
    }

    public static void clear() {
        id.remove();
    }
}