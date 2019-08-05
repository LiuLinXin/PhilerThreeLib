package com.philerlib.eventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by philer on 2019/8/5.
 * Describe:模仿Eventbus的常用基本使用方式(非完整功能)搭建一个类似的库
 * EventBus库:https://github.com/greenrobot/EventBus
 */
public class EventbusPhiler {

    private static EventbusPhiler eventbusPhiler;

    //这儿使用两次判空，防止由于java粒子性导致出现异常
    public static EventbusPhiler getInstance() {
        if (eventbusPhiler == null) {
            synchronized (EventbusPhiler.class) {
                if (eventbusPhiler == null) {
                    eventbusPhiler = new EventbusPhiler();
                }
            }
        }
        return eventbusPhiler;
    }

    private MethodFinder methodFinder = new MethodFinder();
    private Map<Class, List<SubscribeRunMethod>> registedRunUse = new HashMap<>();//每个参数对应的所有可以被运行的对象,post的时候使用
    private Map<Object, List<SubscribeRunMethod>> registedUnregistUse = new HashMap<>();//每个对象注册的所有方法，unregist的时候使用

    private RunEventInterface runEventMainThread;//当前线程运行
    private RunEventInterface runEventNewThread;//新的线程运行

    private EventbusPhiler(){
        runEventMainThread = new RunEvenMain();
        runEventNewThread = new RunEvenNewThread();
    }

    //注册一个事件
    public void registEvent(Object object) {
        List<SubscribeRunMethod> method = methodFinder.findMethod(object);
        if (method == null || method.size() == 0) {
            throw new ExceptionEventBusPhiler("EventbusPhiler exception : 此对象中没有符合条件的方法");
        }

        //添加符合条件的方法到内存中
        for (SubscribeRunMethod subscribeRunMethod : method) {
            addOneSubscribeRunMethod(object, subscribeRunMethod);
        }

        //添加注册的方法到内存中
        List<SubscribeRunMethod> subscribeRunMethods = registedUnregistUse.get(object);
        if (subscribeRunMethods == null || subscribeRunMethods.size() == 0) {
            subscribeRunMethods = new ArrayList<>();
            registedUnregistUse.put(object, subscribeRunMethods);
        }
        for (SubscribeRunMethod subscribeRunMethod : method) {
            subscribeRunMethods.add(subscribeRunMethod);
        }
    }

    //添加某个可以被运行的方法到内存
    private void addOneSubscribeRunMethod(Object object, SubscribeRunMethod subscribeRunMethod) {
        List<SubscribeRunMethod> subscribeRunMethods = registedRunUse.get(subscribeRunMethod.methodParames);
        if (subscribeRunMethods == null) {
            subscribeRunMethods = new ArrayList<>();
            registedRunUse.put(subscribeRunMethod.methodParames, subscribeRunMethods);
        }
        for (SubscribeRunMethod runMethod : subscribeRunMethods) {
            if (runMethod.equals(subscribeRunMethod)) {
                return;
            }
        }
        subscribeRunMethods.add(subscribeRunMethod);
    }

    //反注册一个事件
    public void unregistEvent(Object object) {
        List<SubscribeRunMethod> subscribeRunMethods = registedUnregistUse.get(object);
        if (subscribeRunMethods == null) {
            return;
        }
        List<SubscribeRunMethod> remove = registedUnregistUse.remove(object);
        for (SubscribeRunMethod subscribeRunMethod : remove) {
            unRegistOneMethod(subscribeRunMethod);
        }
    }

    //反注册某一个方法
    private void unRegistOneMethod(SubscribeRunMethod subscribeRunMethod) {
        List<SubscribeRunMethod> subscribeRunMethods1 = registedRunUse.get(subscribeRunMethod.methodParames);
        for (SubscribeRunMethod runMethod : subscribeRunMethods1) {
            if (runMethod.equals(subscribeRunMethod)) {
                subscribeRunMethods1.remove(runMethod);
                return;
            }
        }
    }

    //发布一个事件
    public void pushEvent(Object object) {
        List<SubscribeRunMethod> subscribeRunMethods = registedRunUse.get(object.getClass());
        for (SubscribeRunMethod subscribeRunMethod : subscribeRunMethods) {
            runOneEvent(subscribeRunMethod, object);
        }
    }

    //执行某一个方法
    private void runOneEvent(final SubscribeRunMethod subscribeRunMethod, final Object object) {
        SubscribePhiler subscribePhiler = subscribeRunMethod.subscribePhiler;
        switch (subscribePhiler.thread()) {
            case SubscribePhiler.THREAD_NEW:
                runEventNewThread.run(new Runnable() {
                    @Override
                    public void run() {
                        runMethod(subscribeRunMethod, object);
                    }
                });
                break;
            case SubscribePhiler.THREAD_NOW:
                runMethod(subscribeRunMethod, object);
                break;
            default:
                runEventMainThread.run(new Runnable() {
                    @Override
                    public void run() {
                        runMethod(subscribeRunMethod, object);
                    }
                });
                break;
        }
    }

    //运行某一个方法
    private void runMethod(SubscribeRunMethod subscribeRunMethod, Object object){
        try {
            subscribeRunMethod.method.invoke(subscribeRunMethod.object, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
