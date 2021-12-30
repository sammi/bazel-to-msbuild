package com.tuware.msbuild.contract.msbuild.solution;

public enum Phase {
    PRE_SOLUTION("preSolution"),
    POST_SOLUTION("postSolution");

    private final String value;

    Phase(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
