package com.imaginamos.usuariofinal.taxisya.Model;

/**
 * Created by javiervasquez on 1/12/17.
 */

public class RequestServiceAddressResponse {
    private boolean success;
    private String service_id;
    private int status_id;
    private int error;

    public boolean getSuccess() {
        return success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
