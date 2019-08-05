package com.philerlib.eventbus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by philer on 2019/8/5.
 * Describe:寻找一个对象中添加有
 * @see SubscribePhiler
 * 注释的方法
 */
public class MethodFinder implements MethodFinderInterface {

    //寻找一个类中符合条件的方法列表
    public List<SubscribeRunMethod> findMethod(Object obj){
        List<SubscribeRunMethod> result = new ArrayList<>();
        Class<?> aClass = obj.getClass();
        while (aClass != null){
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                //判断这个方法是否添加了需要监听的注解
                SubscribePhiler annotation = declaredMethod.getAnnotation(SubscribePhiler.class);
                if(annotation == null){
                    continue;
                }
                //判断方法参数个数是否正确
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                if(parameterTypes.length != 1){
                    throw new ExceptionEventBusPhiler("监听的方法参数应该只有一个");
                }
                SubscribeRunMethod subscribeRunMethod = new SubscribeRunMethod(obj, declaredMethod, parameterTypes[0], annotation);
                result.add(subscribeRunMethod);
            }
            Class<?> superclass = aClass.getSuperclass();
            String name = superclass.getName();
            //忽略一些Android系统代码的遍历，优化性能(这个地方目前只忽略了一部分)
            if(name.contains("android.app") || name.contains("fragment.app")){
                break;
            }
            aClass = superclass;
        }
        return result;
    }

}
