package com.jj.elasticsearch.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.elasticsearch.entity.Blog;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DeepRedApple
 */
public class TestClient {

    TransportClient client = null;

    public static final String INDEX = "fendo";

    public static final String TYPE = "fendodate";

    @Before
    public void beforeClient() throws UnknownHostException {
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(new InetSocketAddress("127.0.0.1",9200)));
    }


    /**
     * 手动方式
     *
     * @throws UnknownHostException
     */
    @Test
    public void JsonDocument() throws UnknownHostException {
        String json = "{" +
                "\"user\":\"deepredapple\"," +
                "\"postDate\":\"2019-08-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        IndexResponse indexResponse = client.prepareIndex(INDEX, TYPE).setSource(json).get();
        System.out.println(indexResponse.getResult());
    }

    /**
     * Map方式
     */
    @Test
    public void MapDocument() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user", "hhh");
        json.put("postDate", "2018-06-28");
        json.put("message", "trying out Elasticsearch");
        IndexResponse response = client.prepareIndex(INDEX, TYPE).setSource(json).get();
        System.out.println(response.getResult());
    }

    /**
     * 使用JACKSON序列化
     */
    @Test
    public void JACKSONDocument() throws JsonProcessingException {
        Blog blog = new Blog();
        blog.setUser("123");
        blog.setPostDate("2018-06-29");
        blog.setMessage("try out ElasticSearch");

        ObjectMapper mapper = new ObjectMapper();
        byte[] bytes = mapper.writeValueAsBytes(blog);
        IndexResponse response = client.prepareIndex(INDEX, TYPE).setSource(bytes).get();
        System.out.println(response.getResult());
    }

    /**
     * 使用XContentBuilder帮助类方式
     */
    @Test
    public void XContentBuilderDocument() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("user", "xcontentdocument")
                .field("postDate", "2018-06-30")
                .field("message", "this is ElasticSearch").endObject();
        IndexResponse response = client.prepareIndex(INDEX, TYPE).setSource(builder).get();
        System.out.println(response.getResult());
    }

}


