/*
 Copyright 2015 esbtools Contributors and/or its affiliates.

 This file is part of esbtools.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.esbtools.gateway.resubmit.service;

import org.esbtools.gateway.resubmit.ResubmitRequest;

public final class ResubmitErrorMessages {

    private static final String INVALID_SYSTEM = "There is no resubmit configuration for this system: ";
    private static final String INCOMPLETE_REQUEST = "One or more required values was not present: ";
    private static final String SYSTEM_NOT_CONFIGURED = "One or more systems is not configured correctly: ";
    private static final String RESYNC_FAILED = "There was a problem resubmiting the selected message: ";

    public static String invalidSystem(String system) {
        return INVALID_SYSTEM + system;
    }

    public static String incompleteRequest(ResubmitRequest resubmitRequest) {
        return INCOMPLETE_REQUEST + resubmitRequest;
    }

    public static String systemNotConfigured(String systemName) {
        return SYSTEM_NOT_CONFIGURED + systemName;
    }

    public static String resubmitFailed(ResubmitRequest resubmitRequest) {
        return RESYNC_FAILED + resubmitRequest;
    }
}
