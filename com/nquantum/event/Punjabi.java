package com.nquantum.event;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Punjabi {
    byte value() default 2;
}
