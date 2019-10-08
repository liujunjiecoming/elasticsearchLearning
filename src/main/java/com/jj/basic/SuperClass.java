package com.jj.basic;

/**
 * @ClassName SuperClasss
 * @Description TODO
 * @Author JJLiu
 * @Date 19-10-6 下午3:47
 * @Version 1.0
 **/
public class SuperClass {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


class SubClass extends SuperClass {
    private String performance;

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }
}

class Test {

    public static void main(String[] args) {
        SuperClass superClass = new SuperClass();

        SubClass subClass;

        if (superClass instanceof SubClass) {
            subClass = (SubClass) superClass;
            subClass.setName("test");
            System.out.println(subClass.getName());
        } else {
            System.out.println("你好");
        }

        //SubClass subClass = (SubClass) superClass;
        // subClass.setName("testSubClass");
        // System.out.println(subClass.getName());


    }

}

















