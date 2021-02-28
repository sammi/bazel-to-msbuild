package com.tuware.msbuild.feature;

public class FeatureException extends Exception {

    public FeatureException() {
    }

    public FeatureException(String message) {
        super(message);
    }

    public FeatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeatureException(Throwable cause) {
        super(cause);
    }
}
