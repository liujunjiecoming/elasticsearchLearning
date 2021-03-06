package com.jj.elasticsearch.documentapis;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName DeleteApiEsTest
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午3:29
 * @Version 1.0
 **/
public class DeleteApiEsTest {

    private RestHighLevelClient client = null;

    //private String hostname = "192.168.196.140";
    private String hostname = "127.0.0.1";

    private int port = 9200;


    @Before
    public void beforeClient() {

        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, "http")
                ));


    }

    @Test
    public void deleteOne() throws IOException {
        DeleteRequest request = new DeleteRequest(
                "posts",
                "doc",
                "1");

        client.delete(request, RequestOptions.DEFAULT);
    }















}
