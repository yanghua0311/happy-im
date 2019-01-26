package com.happy.im.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operation {
    //方法匹配
    String value() default  "";
}
