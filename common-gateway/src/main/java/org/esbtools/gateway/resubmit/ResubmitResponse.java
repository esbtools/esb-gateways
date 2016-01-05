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
package org.esbtools.gateway.resubmit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ResubmitResponse {

    public enum Status {
        Success, Error
    }

    private Status status = Status.Success;
    private String errorMessage;

    public ResubmitResponse() {

    }

    public ResubmitResponse(Status status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        this.setStatus(Status.Error);
    }

    public String toJson() {
        String thisJson;
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            thisJson = ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return thisJson;
    }

    @Override
    public String toString() {
        return String.format("ResubmitResponse [status=%s, errorMessage=%s]", status, errorMessage);
    }

}