package com.ai;
@Description("I am interface")
public interface InterfacePerson {
    @Description("I am interface method")
    public String name();
    public int age();

    @Deprecated
    public void sing();

}
