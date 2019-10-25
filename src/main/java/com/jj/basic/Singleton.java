package com.jj.basic;

import com.google.common.collect.Lists;

/**
 * @ClassName Singleton
 * @Description 单例
 * @Author JJLiu
 * @Date 19-10-7 下午2:11
 * @Version 1.0
 **/
public class Singleton {

}

/**
 * 饿汉式单例
 */
class Student {

    // 构造私有，外界就不能造对象了
    public Student() {
    }

    // 在成员变量位置自己创建一个对象
    // 静态方法只能访问静态成员变量，所以成员变量加静态修饰
    // 为了不让外界直接访问修改这个成员变量的值，所以该成员变量加private修饰
    private static Student student = new Student();

    // 提供公共的访问方式，返回该对象。为了保证外界能够直接访问该方法，所以方法加静态修饰
    public static Student getStudent() {
        return student;
    }
}

/**
 * 懒汉式单例
 */
class Teacher {

    public Teacher() {
    }

    private static Teacher t = null;

    public static synchronized Teacher getTeacher() {
        if (t == null) {
            t = new Teacher();
        }
        return t;
    }

}




