package com.tuware.msbuild.domain.solution;

public enum Phase {
    PRE_SOLUTION("presolution"),
    POST_SOLUTION("postSolution");

    private String value;

    public String getValue() {
        return value;
    }

    private Phase(String value) {
        this.value = value;
    }
}