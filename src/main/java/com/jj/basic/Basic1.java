package com.jj.basic;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @ClassName Basic1
 * @Description TODO
 * @Author JJLiu
 * @Date 19-10-5 上午10:09
 * @Version 1.0
 **/
public class Basic1 {

    @Test
    public void test1() {
        System.out.println(2 << 2);

        String[] arr = new String[]{"a", "b", "c", "d"};
        System.out.println(arr.length);

        List<String> list = Lists.newArrayList();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");

        System.out.println(list.size());

        char cn = '中';
    }


}

/**
 * Java中非静态内部类对象的创建要依赖其外部类对象，上面的面试题中foo和main方法都是静态方法，
 * 静态方法中没有this，也就是说没有所谓的外部类对象，因此无法创建内部类对象，
 * 如果要在静态方法中创建内部类对象，可以这样做：new Outer().new Inner();
 */
class Outer {
    class Inner1 {
    }

    public static void foo() {
        new Outer().new Inner1();
        //new Inner1();
    }

    public void bar() {
        new Inner1();
    }

    public static void main(String[] args) {
        new Outer().new Inner1();
        new Inner2();
        //new Inner1();
    }

    static class Inner2 {
    }

    public static void foo1() {
        new Inner2();
    }

    public void bar2() {
        new Inner2();
    }


}