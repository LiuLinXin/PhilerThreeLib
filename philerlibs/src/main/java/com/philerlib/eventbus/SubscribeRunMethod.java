package com.philerlib.eventbus;

import java.lang.reflect.Method;

/**
 * Created by philer on 2019/8/5.
 * Describe:包含可以被运行的方法的一些信息
 */
public class SubscribeRunMethod {
    public Object object;//需要运行的对象
    public Method method;//需要运行的方法
    public Class methodParames;//方法的参数
    public SubscribePhiler subscribePhiler;//方法上的一些配置

    public SubscribeRunMethod(Object object, Method method, Class methodParames, SubscribePhiler subscribePhiler) {
        this.object = object;
        this.method = method;
        this.methodParames = methodParames;
        this.subscribePhiler = subscribePhiler;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SubscribeRunMethod)){
            return false;
        }
        if (((SubscribeRunMethod) obj).object == object && ((SubscribeRunMethod) obj).methodParames == methodParames && ((SubscribeRunMethod) obj).method.getName().equals(method.getName())){
            return true;
        }
        return false;
    }
}
