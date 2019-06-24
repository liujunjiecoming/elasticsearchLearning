package com.jj.elasticsearch.indices_apis;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * @ClassName CreateIndexEsTest
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午1:59
 * @Version 1.0
 **/
public class CreateIndexEsTest {

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
    public void testSimple() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        request.settings(Settings.builder()
                                 .put("index.number_of_shards", 3)
                                 .put("index.number_of_replicas", 2)
        );

        request.mapping(
                "{\n" +
                        "  \"properties\": {\n" +
                        "    \"message\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);

        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }

    @Test
    public void testCreateByMap() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("facebook");
        HashMap<String, Object> message = new HashMap<>();
        message.put("type", "text");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("message", message);
        HashMap<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);

        request.mapping(mapping);
        request.alias(new Alias("facebook_alias").filter(QueryBuilders.termQuery("user", "kimchy")));

        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response.index());

    }

    @Test
    public void testCreateByXContentBuilder() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("Instagram");
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("message");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping(builder);


    }

    @Test
    public void testWholeSource() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("weibo");
        request.source("{\n" +
                "    \"settings\" : {\n" +
                "        \"number_of_shards\" : 1,\n" +
                "        \"number_of_replicas\" : 0\n" +
                "    },\n" +
                "    \"mappings\" : {\n" +
                "        \"properties\" : {\n" +
                "            \"message\" : { \"type\" : \"text\" }\n" +
                "        }\n" +
                "    },\n" +
                "    \"weibo\" : {\n" +
                "        \"sina_weibo\" : {}\n" +
                "    }\n" +
                "}", XContentType.JSON);

        client.indices().create(request, RequestOptions.DEFAULT);

    }


}
