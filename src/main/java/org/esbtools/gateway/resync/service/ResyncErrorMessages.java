package org.esbtools.gateway.resync.service;

import org.esbtools.gateway.resync.ResyncRequest;

public final class ResyncErrorMessages {

    public static final String INVALID_SYSTEM = "There is no resync configuration for this system: ";
    public static final String INCOMPLETE_REQUEST = "One or more required values was not present: ";
    public static final String SYSTEM_NOT_CONFIGURED = "One or more systems is not configured correctly: ";
    public static final String RESYNC_FAILED = "There was a problem resyncing the selected message: %s";

    public static String invalidSystem(String system) {
        return INVALID_SYSTEM + system;
    }

    public static String incompleteRequest(ResyncRequest resyncRequest) {
        return INCOMPLETE_REQUEST + resyncRequest;
    }

    public static String systemNotConfigured(String systemName) {
        return SYSTEM_NOT_CONFIGURED + systemName;
    }

    public static String resyncFailed(ResyncRequest resyncRequest) {
        return RESYNC_FAILED + resyncRequest;
    }
}
