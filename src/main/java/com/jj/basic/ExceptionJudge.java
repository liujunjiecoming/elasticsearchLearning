package com.jj.basic;

/**
 * @ClassName ExceptionJudge
 * @Description
 * @Author JJLiu
 * @Date 19-10-7 上午9:11
 * @Version 1.0
 **/
public class ExceptionJudge {
    public static void main(String[] args) {
        try {
            throw new ExampleB("b");
        } catch (ExampleA a) {
            System.out.println("ExampleA");
        } catch (Exception e) {
            System.out.println("ExampleB");
        }
    }
}


class ExampleA extends Exception {
}

class ExampleB extends ExampleA {
    public ExampleB(String b) {
        System.out.println("我是例子B");
    }

    public ExampleB() {
    }
}

/**
 * 根据里氏代换原则[能使用父类型的地方一定能使用子类型], 抓取ExampleA类型异常的catch块能够抓住try块中抛出的ExampleB类型的异常
 */
class TestException {
    public static void main(String[] args) {
        try {
            try {
                throw new ExampleB();
            } catch (ExampleA a) {
                System.out.println("Caught A");
                throw a;
            }
        } catch (ExampleB b) {
            System.out.println("Caught B");
            return;
        } finally {
            System.out.println("HelloWorld!");
        }

    }

}
























