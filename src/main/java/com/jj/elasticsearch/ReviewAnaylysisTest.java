package com.jj.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @ClassName ReviewAnaylysisTest
 * @Description TODO
 * @Author JJLiu
 * @Date 19-9-16 上午9:04
 * @Version 1.0
 **/
public class ReviewAnaylysisTest {

    private RestHighLevelClient client = null;

    private RestHighLevelClient client1 = null;

    private String hostname = "192.168.196.214";
    private String hostname1 = "192.168.196.140";
    //private String hostname = "192.168.196.152";
    //private String hostname = "127.0.0.1";

    private int port = 9200;

    private int port1 = 9200;

    @Before
    public void beforeClient() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, "http")
                ));
    }

    @Before
    public void beforeClient1() {
        client1 = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname1, port1, "http")
                ));
    }

    @Test
    public void test1() throws IOException {
        String id = "1171980930298200065";
        String taskNo = "RA34108930699296768";
        String reviewText = test3();
        HashMap<String, String> map = new HashMap<>();
        map.put("taskNo", taskNo);
        map.put("reviewText", reviewText);

        IndexRequest indexRequest = new IndexRequest("reviews_analysis").id(id).source(map);
        client.index(indexRequest, RequestOptions.DEFAULT);
    }

    @Test
    public void test2() throws IOException {
        SearchRequest searchRequest = new SearchRequest("reviews_analysis");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        TermQueryBuilder query = QueryBuilders.termQuery("taskNo", "RA35559197655433216");
        builder.query(query);
        searchRequest.source(builder);

        SearchResponse response = client1.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response.getHits().getHits()[0].getSourceAsMap().get("reviewText"));

    }

    private String test3() {
        String str = ReadFile("/home/liujunjie/文档/test.txt");
        return str;
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
