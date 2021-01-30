package com.assistant.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
public @interface DV {

    boolean nullable() default true;

    int maxLength() default 0;

    int minLength() default 0;

    String desc() default "";

    String format() default "";

}
