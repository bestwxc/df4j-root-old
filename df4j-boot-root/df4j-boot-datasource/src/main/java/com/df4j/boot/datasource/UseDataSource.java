package com.df4j.boot.datasource;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UseDataSource {
    String value() default "";
}
