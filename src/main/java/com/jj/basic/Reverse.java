package com.jj.basic;

/**
 * @ClassName Reverse
 * @Description 用递归实现字符串反转
 * @Author JJLiu
 * @Date 19-10-6 上午9:44
 * @Version 1.0
 **/
public class Reverse {

    public static String reverse(String originStr) {
        if (originStr == null || originStr.length() <= 1) {
            return originStr;
        }
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }

    public static void main(String[] args) {
        System.out.println(reverse("abcd"));

        String str = "abcdefg";
        System.out.println(str.substring(1) + str.charAt(0));

    }


}
