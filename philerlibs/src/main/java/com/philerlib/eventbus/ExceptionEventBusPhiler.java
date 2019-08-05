package com.philerlib.eventbus;

/**
 * Created by philer on 2019/8/5.
 * Describe:EventbusPhiler可能会出现的异常
 */
public class ExceptionEventBusPhiler extends RuntimeException {
    public ExceptionEventBusPhiler(String message) {
        super("ExceptionEventBusPhiler : "+message);
    }
}
