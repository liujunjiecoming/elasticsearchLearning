package com.jj.elasticsearch.documentapis;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName IndexApiEsTest
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午2:47
 * @Version 1.0
 **/
public class IndexApiEsTest {

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


    /**
     * 6.8和7.1版本问题，7.1开始不用设置type,会有默认的type
     *
     * @throws IOException
     */
    @Test
    public void CreateOne() throws IOException {
        IndexRequest request = new IndexRequest("posts");
        request.type("doc");
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);

    }

    @Test
    public void CreateTwo() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch twice");
        IndexRequest indexRequest = new IndexRequest("posts")
                .type("doc")
                .id("2")
                .source(jsonMap);

        client.index(indexRequest, RequestOptions.DEFAULT);

    }

    @Test
    public void CreateThree() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "luca");
            builder.timeField("postDate", new Date());
            builder.field("message", "HelloWorld");
        }
        builder.endObject();
        IndexRequest indexRequest = new IndexRequest("posts")
                .type("doc")
                .id("5")
                .source(builder);

        client.index(indexRequest, RequestOptions.DEFAULT);
    }

    @Test
    public void CreateFour() throws IOException {
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");

        client.index(indexRequest, RequestOptions.DEFAULT);
    }

    /**
     * If there is a version conflict, an ElasticsearchException will be thrown:
     *
     * @throws IOException
     */
    @Test
    public void CreateFive() throws IOException {
        IndexRequest request = new IndexRequest("posts")
                .id("1")
                .source("field", "value")
                .setIfSeqNo(10L)
                .setIfPrimaryTerm(20);
        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {

            }
        }
    }


}
