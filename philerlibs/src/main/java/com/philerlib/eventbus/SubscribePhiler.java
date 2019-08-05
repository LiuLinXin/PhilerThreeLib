package com.philerlib.eventbus;

import java.lang.annotation.*;

/**
 * Created by philer on 2019/8/5.
 * Describe:注解类，用于表示某一个方法会被Eventbus注册
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscribePhiler {

    int THREAD_MAIN = 1;//运行在主线程
    int THREAD_NEW = 2;//运行在新的子线程
    int THREAD_NOW = 3;//运行在当前线程

    /**
     * 当前方法运行在哪个线程
     * @return
     */
    int thread() default THREAD_MAIN ;
}
