package com.jj.basic;

/**
 * @ClassName TryCatch
 * @Description TODO
 * @Author JJLiu
 * @Date 19-10-10 下午1:53
 * @Version 1.0
 **/
public class TryCatch {

    public static void main(String[] args) {
        tryCatchReturn("I am return");
    }

    public static String tryCatchReturn(String str) {
        try {
            System.out.println("I am try");
        } catch (Exception e) {
            System.out.println("I am catch");
        } finally {
            System.out.println("I am finally");
            return str;
        }
    }

}
