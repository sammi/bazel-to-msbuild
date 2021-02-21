package com.tuware.msbuild.model;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface NamedArg {
    String value();
    String defaultValue() default "";
}