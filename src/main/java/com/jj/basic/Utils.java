package com.jj.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @ClassName Utils
 * @Description TODO
 * @Author JJLiu
 * @Date 19-10-7 上午10:47
 * @Version 1.0
 **/
public class Utils {
    public static void main(String[] args) {
        System.out.println(MyUtil.countWordInFile("/home/liujunjie/文档/test.txt", "Use"));
    }
}


class MyUtil {
    // 工具类中的方法都是静态方式访问的因此将构造器私有不允许创建对象(绝对好习惯)
    private MyUtil() {
        throw new AssertionError();
    }

    /**
     * 统计给定文件中给定字符串的出现次数
     *
     * @param filename 文件名
     * @param word     字符串
     * @return 字符串在文件中出现的次数
     */
    public static int countWordInFile(String filename, String word) {
        int counter = 0;
        try (FileReader fr = new FileReader(filename)) {
            try (BufferedReader br = new BufferedReader(fr)) {
                // System.out.println("br:  " + br.readLine());
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    System.out.println(line.indexOf(word));
                    int index = -1;
                    while (line.length() >= word.length() && (index = line.indexOf(word)) >= 0) {
                        counter++;
                        line = line.substring(index + word.length());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return counter;
    }
}


class TestFile {

    /**
     * 列出一个目录下所有的文件
     *
     * @param args
     */
    public static void main(String[] args) {
        //展开file路径下的所有文件夹
        showDirectory(new File("/home/liujunjie/文档"));


        // File f = new File("/home/liujunjie/文档");
        // for (File temp : f.listFiles()) {
        //     if (temp.isFile()) {
        //         System.out.println(temp.getName());
        //     }
        // }
    }

    public static void showDirectory(File f) {
        _walkDirectory(f, 0);
    }

    private static void _walkDirectory(File f, int level) {
        //判断如果是文件夹的话,就展开该文件夹下的文件
        if (f.isDirectory()) {
            for (File temp : f.listFiles()) {
                //递归循环
                _walkDirectory(temp, level + 1);
            }
        } else {
            for (int i = 0; i < level - 1; i++) {
                System.out.print("\t");
            }
            System.out.println(f.getName());
        }
    }
}


class ShowFile {

    /**
     * 在Java 7中可以使用NIO.2的API来做同样的事情
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Path initPath = Paths.get("/home/liujunjie/文档");
        Files.walkFileTree(initPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                System.out.println(file.getFileName().toString());
                return FileVisitResult.CONTINUE;
            }

        });
    }
}


/**
 * 冒泡排序
 */
class SortUtil {

    public static void main(String[] args) {
        int[] arr1 = {1, 1, 2, 0, 9, 3, 12, 7, 8, 3, 4, 65, 22};

        //第一种排序方法
        bubbleSort1(arr1, arr1.length);
        for (int i : arr1) {
            System.out.print(i + ",");
        }

        System.out.println("\n");

        int[] arr2 = {0, 1, 1, 2, 3, 3, 4, 9, 12, 7, 8, 65, 22};
        //第二种排序方法
        bubbleSort2(arr2, arr2.length);
        for (int i : arr2) {
            System.out.print(i + ",");
        }

        System.out.println("\n");

        int[] arr3 = {0, 1, 1, 2, 3, 3, 4, 9, 12, 7, 8, 65, 22};
        bubbleSort3(arr3, arr3.length);

    }

    public static void bubbleSort1(int[] arr, int length) {
        int i, j;
        for (i = 0; i < length; i++) {
            for (j = 1; j < length - i; j++) {
                if (arr[j - 1] > arr[j]) {
                    int temp;
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    System.out.println("i:" + i + " 大的值:" + temp + "  小的值:" + arr[j - 1]);
                }
            }
        }
    }

    /**
     * 设置一个标志，如果这一趟发生了交换，则为true，否则为false。明显如果有一趟没有发生交换，说明排序已经完成。
     *
     * @param a
     * @param n
     */
    public static void bubbleSort2(int[] a, int n) {
        int j, k = n;
        boolean flag = true;//发生了交换就为true, 没发生就为false，第一次判断时必须标志位true。
        while (flag) {
            flag = false;//每次开始排序前，都设置flag为未排序过
            for (j = 1; j < k; j++) {
                if (a[j - 1] > a[j]) {//前面的数字大于后面的数字就交换
                    //交换a[j-1]和a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;

                    System.out.println(" 大的值:" + temp + "  小的值:" + a[j - 1]);
                    //表示交换过数据;
                    flag = true;
                }
            }
            k--;//减小一次排序的尾边界
        }//end while
    }//end

    public static void bubbleSort3(int[] a, int n) {
        int j, k;
        int flag = n;//flag来记录最后交换的位置，也就是排序的尾边界

        while (flag > 0) {//排序未结束标志
            k = flag; //k 来记录遍历的尾边界
            flag = 0;

            for (j = 1; j < k; j++) {
                if (a[j - 1] > a[j]) {//前面的数字大于后面的数字就交换
                    //交换a[j-1]和a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;

                    //表示交换过数据;
                    flag = j;//记录最新的尾边界.
                }
            }
            System.out.println(flag);
        }
    }

}




















