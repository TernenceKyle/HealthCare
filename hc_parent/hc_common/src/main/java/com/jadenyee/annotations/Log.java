package com.jadenyee.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String OPERA_TYPE_DEL = "2";
    String OPERA_TYPE_EDIT = "1";
    String OPERA_TYPE_SELECT = "0";
    public String operation() default Log.OPERA_TYPE_SELECT;
    public String title();
}
