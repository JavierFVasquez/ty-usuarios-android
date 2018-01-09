package com.imaginamos.usuariofinal.taxisya.models;

/**
 * Created by javiervasquez on 1/12/17.
 */

public class CancelServiceResponse {
    private boolean success;
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

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
