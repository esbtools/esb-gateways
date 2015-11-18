package org.esbtools.gateway.resync;

public class ResyncResponse {

    public enum Status {
        Success, Error
    }

    private Status status = Status.Success;
    private String errorMessage;

    public ResyncResponse() {

    }

    public ResyncResponse(Status status, String errorMessage) {
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

    public boolean wasSuccessful() {
        return Status.Success.equals(status) ? true : false;
    }

    @Override
    public String toString() {
        return String.format("ResyncResponse [status=%s, errorMessage=%s]", status, errorMessage);
    }

}