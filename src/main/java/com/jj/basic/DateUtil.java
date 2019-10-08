package com.jj.basic;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName DateUtil
 * @Description TODO
 * @Author JJLiu
 * @Date 19-10-6 上午10:05
 * @Version 1.0
 **/
public class DateUtil {

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH) + 1); // 0 - 11
        System.out.println(cal.get(Calendar.DATE));
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
        System.out.println(cal.get(Calendar.MINUTE));
        System.out.println(cal.get(Calendar.SECOND));

        System.out.println("=============分割线===============");

        // Java 8
        LocalDateTime dt = LocalDateTime.now();
        System.out.println(dt.getYear());
        System.out.println(dt.getMonthValue()); // 1 - 12
        System.out.println(dt.getDayOfMonth());
        System.out.println(dt.getHour());
        System.out.println(dt.getMinute());
        System.out.println(dt.getSecond());

        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(System.currentTimeMillis());
        System.out.println(Clock.systemDefaultZone().millis()); //Java8

    }
}


class DateFormat {

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        System.out.println(date);

        // Java 8
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(LocalDate.now());
        LocalDate now = LocalDate.now();
        System.out.println(dateTimeFormatter.format(now));
    }

}

/**
 * 打印昨天的当前时刻
 */
class YesterdayCurrent {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        System.out.println(calendar.getTime());
        calendar.add(Calendar.MONTH,-1);
        System.out.println(calendar.getTime());


        // Java 8
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);
        System.out.println(yesterday);
    }

}


























