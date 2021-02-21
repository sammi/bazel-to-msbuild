package com.tuware.msbuild.contract.adapter;

public class AdapterException extends Exception {

    public AdapterException(String message) {
        super(message);
    }

    public AdapterException(Throwable cause) {
        super(cause);
    }

    public AdapterException(String message, Throwable cause) {
        super(message, cause);
    }
}
