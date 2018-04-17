package com.ai;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 解析注解
 * 只有运行时注解才可以解析
 */
public class ParseAnnotation {
    public static void main(String[] args) {

        try {
            //1、使用类加载器加载类
            Class c=Class.forName("com.ai.Child");
            //2、找到类上的面注解
            //判断类上面是否存在这个注解
            boolean isExist=c.isAnnotationPresent(Description.class);
            if (isExist){
                //3、拿到注解实例
                Description description=(Description)c.getAnnotation(Description.class);
                System.out.println(description.value());
            }
            //4、找到方法上的注解
           Method[] methods = c.getMethods();

            for (Method m:methods) {
                boolean isMExist=m.isAnnotationPresent(Description.class);
                if(isMExist){
                    Description description=(Description)m.getAnnotation(Description.class);

                    System.out.println(description.value());
                }
            }

            //另一种解析的方法
            for(Method m:methods){
                Annotation[] as=m.getAnnotations();
                for (Annotation a:as){
                    if (a instanceof Description){
                        Description description=(Description)a;
                        System.out.println(description.value());
                    }
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
