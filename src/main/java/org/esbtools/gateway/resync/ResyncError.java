package org.esbtools.gateway.resync;

public final class ResyncError {

    public static final String ALL_REQUIRED_VALUES_NOT_PRESENT = "One or more required values was not present";
    public static final String SYSTEM_NOT_CONFIGURED = "The %s system is not configured properly";
    public static final String PROBLEM_ENQUEUING = "There was a problem enqueuing the selected message: %s";

    public static String withContext(String errorMessage, String variableValue) {
        return String.format(errorMessage, variableValue);
    }

}
