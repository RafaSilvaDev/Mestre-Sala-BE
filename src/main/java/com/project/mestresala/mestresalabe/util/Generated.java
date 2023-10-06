package com.project.mestresala.mestresalabe.util;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/*
 * Annotation to make classes be excluded from code coverage analysis.
 */

@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Generated {
}
