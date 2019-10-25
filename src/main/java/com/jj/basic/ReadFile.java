package com.jj.basic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ReadFile
 * @Description TODO
 * @Author JJLiu
 * @Date 19-10-23 上午9:13
 * @Version 1.0
 **/
public class ReadFile {

    private static String[] dimensions = {
            "Look & Feel-Size-positive",
            "Look & Feel-Size-negative",
            "Look & Feel-Color & Style-positive",
            "Look & Feel-Color & Style-negative",
            "Look & Feel-Weight-positive",
            "Look & Feel-Weight-negative",
            "Look & Feel-Taste & Smelling-positive",
            "Look & Feel-Taste & Smelling-negative",
            "Look & Feel-Brand-positive",
            "Look & Feel-Brand-negative",
            "Look & Feel-Sound-positive",
            "Look & Feel-Sound-negative",
            "About the product-Cost performance-positive",
            "About the product-Cost performance-negative",
            "About the product-Cost performance-neutral",
            "About the product-Performance & Quality-positive",
            "About the product-Performance & Quality-negative",
            "Usage Scenarios-Time-Usage period",
            "Usage Scenarios-Time-Frequency of use",
            "Usage Scenarios-Location",
            "Usage Scenarios-Person-Friend Use",
            "Usage Scenarios-Person-Family Use",
            "Usage Scenarios-Person-Pet Use",
            "Usage Scenarios-Person-Other Use",
            "Usage Scenarios-Person-Occupation",
            "Usage Scenarios-Others",
            "Product Page Description-positive",
            "Product Page Description-Negative",
            "User profile",
            "Seller relative-Logistics-positive",
            "Seller relative-Logistics-negative",
            "Seller relative-Seller Performance-positive",
            "Seller relative-Seller Performance-negative",
            "Overall feeling-positive",
            "Overall feeling-negative",
            "Overall feeling-neutral",
    };

    private static JSONObject tags = new JSONObject();
    private static JSONObject cluster_center;


    public static void main(String[] args) {

        String jsonStr = ReadFile("/home/liujunjie/音乐/5.json");
        System.out.println(jsonStr);
        System.out.println("------------------------------------------------- \n");

        long startTime = System.currentTimeMillis();
        JSONObject result = parseTogether(jsonStr);
        //String re = JSON.toJSONString(result, SerializerFeature.PrettyFormat);

        long endTime = System.currentTimeMillis();
        long usedTime = (endTime - startTime);

        System.out.println("\n-------------------------------------------------");
        System.out.println(result);
        System.out.println(usedTime);


    }

    private static JSONObject parseTogether(String input) {
        JSONObject obj1 = JSONObject.parseObject(input);
        HashMap<String, Integer> dates = parserDate(obj1.getJSONObject("raw_reviews"));
        cluster_center = JSONObject.parseObject(obj1.getString("cluster_center"));
        System.out.println("cluster_center1111111111111: " + cluster_center);
        for (String di : dimensions) {
            tags.put(di, new JSONObject());
        }
        JSONArray reviews = obj1.getJSONArray("reviews");
        System.out.println("cluster_center2222222222222: " + cluster_center);
        System.out.println("reviews: " + reviews);
        for (int i = 0; i < reviews.size(); i++) {
            parseOne(reviews.getJSONObject(i));
        }

        System.out.println("cluster_center3333333333333: " + cluster_center);
        for (String di : cluster_center.keySet()) {
            if (cluster_center.getJSONObject(di).size() != 0) {
                for (String cluster_id : cluster_center.getJSONObject(di).keySet()) {
                    JSONObject ele = new JSONObject();
                    ele.put("num", cluster_center.getJSONObject(di).getJSONObject(cluster_id).getInteger("num"));
                    ele.put("sent", cluster_center.getJSONObject(di).getJSONObject(cluster_id).getJSONArray("sent"));
                    tags.getJSONObject(di).put(cluster_center.getJSONObject(di).getJSONObject(cluster_id).getString("word"), ele);
                    if (cluster_center.getJSONObject(di).getJSONObject(cluster_id).getString("word").equals("I'm a pale goth")) {
                        System.out.println("keySet: " + cluster_center.getJSONObject(di).keySet());
                        System.out.println("             " + cluster_center.getJSONObject(di).getJSONObject(cluster_id));
                        System.out.println("tags: " + tags);
                        System.out.println("TEST111");
                        System.out.println("ele: " + ele);
                    }
                }
            }
        }

        // System.out.println("tags: " + tags);

        JSONObject data = new JSONObject();
        data.put("hits", tags);
        data.put("dates", dates);
        data.put("raw", obj1.getJSONObject("raw_reviews"));

        JSONObject result = new JSONObject();
        result.put("code", 100);
        result.put("data", data);
        result.put("msg", "Success!");
        result.put("success", true);

        return result;

    }

    private static void parseOne(JSONObject review) {
        JSONObject titles = review.getJSONObject("title");
        JSONObject reviews = review.getJSONObject("review");

        String metaWithoutCluster;
        String metaWithCluster;


        if (titles.size() != 0) {
            for (String di : titles.keySet()) {
                JSONArray per_di = titles.getJSONArray(di);
                for (int ele = 0; ele < per_di.size(); ele++) {
                    JSONObject per_ele = per_di.getJSONObject(ele);
                    metaWithoutCluster = per_ele.getString("text").toLowerCase();
                    metaWithCluster = per_ele.getString("cluster_id");

                    if (!per_ele.containsKey("cluster_id")) {
                        if (tags.getJSONObject(di).get(metaWithoutCluster) == null) {
                            JSONObject meta = new JSONObject();
                            meta.put("count", 0);
                            meta.put("sent", new JSONArray());
                            tags.getJSONObject(di).put(metaWithoutCluster, meta);
                        }
                        Integer old_count = tags.getJSONObject(di).getJSONObject(metaWithoutCluster).getInteger("count");
                        tags.getJSONObject(di).getJSONObject(metaWithoutCluster).put("count", old_count + 1);
                        List<String> sent = Arrays.asList(per_ele.getString("start"), per_ele.getString("end"), review.getString("review_id"), "title");
                        tags.getJSONObject(di).getJSONObject(metaWithoutCluster).getJSONArray("sent").add(sent);
                    } else {
                        if (cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).getJSONArray("sent") == null) {
                            cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).put("sent", new JSONArray());
                            List<String> sent = Arrays.asList(per_ele.getString("start"), per_ele.getString("end"), review.getString("review_id"), "title");
                            cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).getJSONArray("sent").add(sent);
                        } else {
                            List<String> sent = Arrays.asList(per_ele.getString("start"), per_ele.getString("end"), review.getString("review_id"), "title");
                            cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).getJSONArray("sent").add(sent);
                        }
                    }
                }
            }
        }
        if (reviews.size() != 0) {
            for (String di : reviews.keySet()) {
                JSONArray per_di = reviews.getJSONArray(di);
                for (int ele = 0; ele < per_di.size(); ele++) {
                    JSONObject per_ele = per_di.getJSONObject(ele);
                    System.out.println("per_ele: " + per_ele);
                    if (per_ele.getString("text").equals("I'm a pale goth")) {
                        System.out.println("牛逼1111111");
                    }
                    metaWithoutCluster = per_ele.getString("text").toLowerCase();
                    metaWithCluster = per_ele.getString("cluster_id");

                    System.out.println(!per_ele.containsKey("cluster_id"));
                    if (!per_ele.containsKey("cluster_id")) {
                        if (tags.getJSONObject(di).get(metaWithoutCluster) == null) {
                            JSONObject meta = new JSONObject();
                            meta.put("count", 0);
                            meta.put("sent", new JSONArray());
                            tags.getJSONObject(di).put(metaWithoutCluster, meta);
                        }
                        Integer old_count = tags.getJSONObject(di).getJSONObject(metaWithoutCluster).getInteger("count");
                        tags.getJSONObject(di).getJSONObject(metaWithoutCluster).put("count", old_count + 1);

                        List<String> sent = Arrays.asList(per_ele.getString("start"), per_ele.getString("end"), review.getString("review_id"), "review");
                        tags.getJSONObject(di).getJSONObject(metaWithoutCluster).getJSONArray("sent").add(sent);
                    } else {
                        if (per_ele.getString("text").equals("I'm a pale goth")) {
                            System.out.println(metaWithoutCluster);
                            System.out.println(tags.getJSONObject(di));
                            System.out.println("卢本伟:" + tags.getJSONObject(di).getJSONObject(metaWithoutCluster));
                            System.out.println("赵梦玥: " + cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).getJSONArray("sent"));
                        }
                        System.out.println("sent1: " + cluster_center.getJSONObject(di).getJSONObject(metaWithCluster));
                        System.out.println("sent2: " + cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).getJSONArray("sent"));
                        if (cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).getJSONArray("sent") == null) {
                            cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).put("sent", new JSONArray());
                            List<String> sent = Arrays.asList(per_ele.getString("start"), per_ele.getString("end"), review.getString("review_id"), "review");
                            cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).getJSONArray("sent").add(sent);
                        } else {
                            List<String> sent = Arrays.asList(per_ele.getString("start"), per_ele.getString("end"), review.getString("review_id"), "review");
                            cluster_center.getJSONObject(di).getJSONObject(metaWithCluster).getJSONArray("sent").add(sent);
                        }
                    }
                }
            }
        }
    }

    private static HashMap<String, Integer> parserDate(JSONObject rawReviews) {
        HashMap<String, Integer> dates = new HashMap<String, Integer>();
        for (String review : rawReviews.keySet()) {
            String date = rawReviews.getJSONObject(review).getString("review_time");
            if (!dates.containsKey(date.substring(0, 7))) {
                dates.put(date.substring(0, 7), 1);
            } else {
                Integer count = dates.get(date.substring(0, 7));
                dates.put(date.substring(0, 7), count + 1);
            }
        }
        return dates;

    }

    private static String ReadFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

}
