package com.tuware.msbuild.adapter.generator;

public class TemplatePathProvider {

    private static final String TEMPLATES_VCXPROJ_HBS = "/templates/vcxproj.hbs";
    private static final String TEMPLATES_VCXPROJ_USER_HBS = "/templates/vcxproj.user.hbs";
    private static final String TEMPLATES_VCXPROJ_FILTERS_HBS = "/templates/vcxproj.filters.hbs";
    private static final String TEMPLATES_SOLUTION_HBS = "/templates/solution.hbs";

    private TemplatePathProvider() {}

    public static String projectTemplate() {
        return TEMPLATES_VCXPROJ_HBS;
    }

    public static String projectUserTemplate() {
        return TEMPLATES_VCXPROJ_USER_HBS;
    }

    public static String projectFilterTemplate() {
        return TEMPLATES_VCXPROJ_FILTERS_HBS;
    }

    public static String solutionTemplate() {
        return TEMPLATES_SOLUTION_HBS;
    }
}
