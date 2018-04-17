package com.ai;

/**
 * @Inherited 子类实现接口是无法继承接口中的注解，只有子类继承父类才能继承父类的注解
 */
@Description("I am Child class annotation")
public class Child extends ClassPerson implements InterfacePerson {

    @Override
    @Description("I am Child method annotation")
    public String name() {
        return null;
    }

    @Override
    public int age() {
        return 0;
    }

    @Override
    public void sing() {

    }

}
