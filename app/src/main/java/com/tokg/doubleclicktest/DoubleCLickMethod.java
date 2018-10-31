package com.tokg.doubleclicktest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Created by Tony Shen on 2016/12/7.
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleCLickMethod {
}
