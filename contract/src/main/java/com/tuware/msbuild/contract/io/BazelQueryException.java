package com.tuware.msbuild.contract.io;

public class BazelQueryException extends Exception {

    public BazelQueryException(String message) {
        super(message);
    }

    public BazelQueryException(Throwable cause) {
        super(cause);
    }
}
