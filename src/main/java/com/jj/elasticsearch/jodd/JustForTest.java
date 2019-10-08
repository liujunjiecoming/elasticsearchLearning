package com.jj.elasticsearch.jodd;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @ClassName JustForTest
 * @Description TODO
 * @Author JJLiu
 * @Date 19-8-9 上午9:03
 * @Version 1.0
 **/
public class JustForTest {

    @Test
    public void dateToMils() {
        String date = "2019-08-06 15:50:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的日期格式，根据实际调整""里面内容
        try {
            long dateToSecond = sdf.parse(date).getTime();//sdf.parse()实现日期转换为Date格式，然后getTime()转换为毫秒数值
            System.out.print(dateToSecond);
            System.out.println("====================");
            System.out.print(dateToSecond / 1000 / 600);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2608463    2019-08-06 15:50:00
     * 2608464    2019-08-06 16:00:00
     * 2608465    2019-08-06 16:10:00
     * 2608466    2019-08-06 16:20:00
     * 2608467    2019-08-06 16:30:00
     * 2608468    2019-08-06 16:40:00
     * 2608469    2019-08-06 16:50:00
     *
     * 1，获取到最小的batch，设置最小那个batch的times为1，没有endTime
     * 2，根据batch分组找到所有的batch值放到一个list中
     * 3，
     */
    @Test
    public void milsToDate() {
        long sd = 2608469 * 600 * 1000L;
        Date dat = new Date(sd);
        GregorianCalendar gc = new GregorianCalendar(); //标准阳历
        gc.setTime(dat); //利用setTime()设置其时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sb = sdf.format(gc.getTime()); //利用format()将日期类型转换为String类型。
        System.out.println(sb);
    }

    @Test
    public void test() {
        Long a = 200L;
        Long b = 200L;
        System.out.println(a.equals(b));
    }


}
