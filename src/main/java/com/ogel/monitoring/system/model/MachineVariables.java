package com.ogel.monitoring.system.model;

public enum MachineVariables {
    PRODUCTION("PRODUCTION"),
    SCRAP("SCRAP"),
    CORE_TEMPERATURE("CORE TEMPERATURE");

    private String value;

    MachineVariables(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MachineVariables of(String value) {
        for (MachineVariables b : MachineVariables.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        return null;
    }
}
