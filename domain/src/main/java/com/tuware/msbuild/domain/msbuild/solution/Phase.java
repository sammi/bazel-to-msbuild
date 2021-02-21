package com.tuware.msbuild.domain.msbuild.solution;

public enum Phase {
    PRE_SOLUTION("presolution"),
    POST_SOLUTION("postSolution");

    private String value;

    public String getValue() {
        return value;
    }

    Phase(String value) {
        this.value = value;
    }
}
