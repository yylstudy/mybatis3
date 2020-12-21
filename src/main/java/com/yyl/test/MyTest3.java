package com.yyl.test;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;


/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/12/5 19:52
 * @Version: 1.0
 */
public class MyTest3 {
    public static void main(String[] args) throws Exception{
        Animal animal = new Animal();
        MetaObject obj = SystemMetaObject.forObject(animal);
        obj.setValue("age",null);
        System.out.println(animal.age);
    }

    static class Animal{
        private int age;
    }

}
