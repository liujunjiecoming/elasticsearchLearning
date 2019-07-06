package com.jj.elasticsearch.keyword_report;

import com.google.common.base.Joiner;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName KeywordReportTest
 * @Description 
 * @Author JJLiu
 * @Date 19-6-25 下午2:35
 * @Version 1.0
 **/
public class KeywordReportTest {

    private RestHighLevelClient client = null;

    //192.168.196.140
    //127.0.0.1
    private String hostname = "192.168.196.140";

    private int port = 9200;

    @Before
    public void beforeClient() {

        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, "http")
                ));

    }

    @Test
    public void test1() throws IOException {
        SearchRequest searchRequest = new SearchRequest("product_report");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(96);
        searchSourceBuilder.size(100);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();

        // long totalHits = hits.getTotalHits();
        // System.out.println(totalHits);
        //
        // float maxScore = hits.getMaxScore();
        // System.out.println(maxScore);

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            //System.out.println(hit.getSourceAsString());

            //String sourceAsString = hit.getSourceAsString();

            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Object gmtCreate = sourceAsMap.get("gmtCreate");
            Object type = sourceAsMap.get("type");
            System.out.println(gmtCreate);
            System.out.println(type);

            //System.out.println(sourceAsMap);

        }

        // System.out.println(searchResponse);

    }

    @Test
    public void test2() {

        double percent = (double) 5 / 15;
        double percent3 = (double) 0 / 1;
        NumberFormat nt = NumberFormat.getPercentInstance();//获取格式化对象
        nt.setMinimumFractionDigits(5);//设置百分数精确度2即保留两位小数
        System.out.println("百分数1：" + nt.format(percent));//最后格式化并输出
        System.out.println("百分数3：" + nt.format(percent3));

        Double a = 6.96;
        Double b = 82.89;

        System.out.println(a / b * 100);
        System.out.println(nt.format(a / b));

    }

    @Test
    public void test3() {
        String a = "12314234";
        String b = "WJOINDSLKAD";
        System.out.println(Joiner.on("/").join(a, b));


        StringBuffer sb = new StringBuffer();
        sb.append(a).append("/").append(b);
        System.out.println(sb);

    }

    @Test
    public void test4() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("G");

        System.out.println(list);

        list = new ArrayList<>();

        System.out.println(list);
    }


    public static void div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        double v = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(v);
    }

    @Test
    public void test6() {
        div(10, 3, 10);
    }


}
