package com.jj.elasticsearch.documentapis;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName ExistsApiEsTest
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午3:16
 * @Version 1.0
 **/
public class ExistsApiEsTest {

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
    public void existOne() throws IOException {
        GetRequest getRequest = new GetRequest(
                "posts",
                "doc",
                "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");

        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);

    }




}
