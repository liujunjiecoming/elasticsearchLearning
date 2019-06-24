package com.jj.elasticsearch.indices_apis;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName AnalyzeRequestEs
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午1:40
 * @Version 1.0
 **/
public class AnalyzeRequestEsTest {

    private RestHighLevelClient client = null;

    private String hostname = "192.168.196.140";

    private int port = 9200;

    @Before
    public void beforeClient() {

        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, "http")
                ));


    }


    /**
     * 简单的AnalyzeRequest测试
     *
     * @throws IOException
     */
    @Test
    public void testSimple() throws IOException {

        AnalyzeRequest request = new AnalyzeRequest();
        request.text("Some text to analyze", "Some more text to analyze");
        request.analyzer("english");

        AnalyzeResponse response = client.indices().analyze(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }

    @Test
    public void testCustomAnalyzer() throws IOException {

        AnalyzeRequest request = new AnalyzeRequest();
        request.text("<b>Some text to analyze</b>");
        request.addCharFilter("html_strip");
        request.tokenizer("standard");
        request.addTokenFilter("lowercase");

//        Map<String, Object> stopFilter = new HashMap<>();
//        stopFilter.put("type", "stop");
//        stopFilter.put("stopwords", new String[]{ "to" });
//        request.addTokenFilter(stopFilter);

        AnalyzeResponse response = client.indices().analyze(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }


}
