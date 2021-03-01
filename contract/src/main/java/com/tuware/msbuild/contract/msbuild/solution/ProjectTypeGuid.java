package com.tuware.msbuild.contract.msbuild.solution;

public enum ProjectTypeGuid {
    CPP("8BC9CEB8-8B4A-11D0-8D11-00A0C91BC942"),
    CSharp("FAE04EC0-301F-11D3-BF4B-00C04F79EFBC");

    private String value;

    ProjectTypeGuid(String value) {
        this.value = value;
    }

    public String getValue() {
        return String.format("{%s}", value);
    }
}
