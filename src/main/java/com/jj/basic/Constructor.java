package com.jj.basic;

/**
 * @ClassName Constructor
 * @Description TODO
 * @Author JJLiu
 * @Date 19-10-6 上午9:25
 * @Version 1.0
 **/


/**
 * 创建对象时构造器的调用顺序是：
 * 1,先初始化静态成员，
 * 2,然后调用父类构造器，
 * 3,再初始化非静态成员，
 * 4,最后调用自身构造器
 */
public class Constructor {
    public static void main(String[] args) {
        A ab = new B();
        //ab = new B();
    }
}


class A {
    private static final String ab = "TEST1";

    static {
        System.out.print("1");
    }

    public A() {
        System.out.println("2");
    }

}


class B extends A {
    static {
        System.out.println("a");
    }

    public B() {
        System.out.println("b");
    }

}




