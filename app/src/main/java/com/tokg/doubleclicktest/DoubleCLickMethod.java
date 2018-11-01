package com.tokg.doubleclicktest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Created by TokG on 18/11/11.
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleCLickMethod {
}
