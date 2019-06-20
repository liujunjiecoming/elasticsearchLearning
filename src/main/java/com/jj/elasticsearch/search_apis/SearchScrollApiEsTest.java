package com.jj.elasticsearch.search_apis;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;

/**
 * @ClassName SearchScrollApiEsTest
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午8:20
 * @Version 1.0
 **/
public class SearchScrollApiEsTest {

    private RestHighLevelClient client = null;

    //private String hostname = "192.168.196.140";
    //private String hostname = "127.0.0.1";
    private String hostname = "192.168.196.140";

    private int port = 9200;

    @Before
    public void beforeClient() {

        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, "http")
                ));

    }













































}
