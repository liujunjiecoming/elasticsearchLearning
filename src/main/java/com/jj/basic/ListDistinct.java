package com.jj.basic;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jj.basic.entity.User;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ListDistinct
 * @Description 集合去重
 * @Author JJLiu
 * @Date 19-10-16 上午9:58
 * @Version 1.0
 **/
public class ListDistinct {

    public static void main(String[] args) {
        // mapDistinct();
        // listSplit();

        System.out.println(10 % 10);
    }


    /**
     * map去重
     */
    private static void mapDistinct() {
        //新建一个集合
        List<User> newList = Lists.newArrayList();
        List<User> distinctList = Lists.newArrayList();
        //获取用户列表
        List<User> userList = getUserList();
        HashMap<String, String> map = Maps.newHashMap();

        for (User user : userList) {
            if (user == null) {
                continue;
            }

            String id = user.getId();
            if (id != null) {
                String value = map.get(id);
                if (StringUtils.isBlank(value)) { //如果value是空的  说明取到的这个name是第一次取到
                    map.put(id, id);
                    //newList就是我们想要的去重之后的结果
                    newList.add(user);
                } else {
                    distinctList.add(user);
                    continue;
                }
            }
        }

        System.out.println("Map去重后的集合： " + newList);
        System.out.println("其他集合： " + distinctList);
    }

    private static List<User> getUserList() {
        List<User> userList = Lists.newArrayList();
        User user = new User();
        user.setId("R276T03PLMC82C");
        user.setName("张三1");
        user.setPassword("1");
        userList.add(user);

        user = new User();
        user.setId("R276T03PABC82C");
        user.setName("李四");
        user.setPassword("2");
        userList.add(user);

        user = new User();
        user.setId("R276EF3PLMC82C");
        user.setName("赵五");
        user.setPassword("3");
        userList.add(user);

        user = new User();
        user.setId("R276T03PLMC82C");
        user.setName("刘六");
        user.setPassword("6");
        userList.add(user);

        return userList;
    }

    private static void listSplit() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i <= 100000; i++) {
            list.add("" + i);
        }
        int size = list.size();
        int num = (size) % 500 == 0 ? (size / 500) : (size / 500 + 1);// 按每500条记录查询
        int start = 0;
        int end = 0;
        List<String> a = new ArrayList<String>();

        for (int i = 1; i <= num; i++) {
            end = (i * 500) > size ? size : (i * 500);
            start = (i - 1) * 500;
            for (; start < end; start++) {
                a.add(list.get(start));
            }
            System.out.println("输出数据---" + a.toString());
            //此处可以进行处理数据  插入 修改删除 都可以进行操作 避免同时操作大集合数据
            a.clear();
        }
    }


}
