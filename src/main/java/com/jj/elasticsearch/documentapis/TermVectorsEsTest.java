package com.jj.elasticsearch.documentapis;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName TermVectorsEsTest
 * @Description TODO
 * @Author JJLiu
 * @Date 19-6-22 下午2:58
 * @Version 1.0
 **/

public class TermVectorsEsTest {

    private RestHighLevelClient client = null;

    //192.168.196.140
    //127.0.0.1
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
    public void testTermVectors() throws IOException {

        //TermVectorsRequest termVectorsRequest = new TermVectorsRequest("authors", "_doc", "1");

        XContentBuilder docBuilder = XContentFactory.jsonBuilder();
        docBuilder.startObject().field("user", "guest-user").endObject();
        TermVectorsRequest request = new TermVectorsRequest("posts", "_doc", docBuilder);

        TermVectorsResponse response = client.termvectors(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }


}
