package com.jj.elasticsearch.company;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName TestExample
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-17 下午3:07
 * @Version 1.0
 **/
public class TestExample {

    RestHighLevelClient client = null;

    //RestClient client = null;


    @Before
    public void beforeClient() throws UnknownHostException {
//        client = RestClient.builder(
//                new HttpHost("localhost", 9300, "http")
//        ).build();

        client = new RestHighLevelClient(
                RestClient.builder(
                        //new HttpHost("192.168.196.140", 9200, "http")));
                        new HttpHost("localhost", 9200, "http")));
    }

    @Test
    public void testCreate() throws IOException {
        IndexRequest request = new IndexRequest(
                "posts",
                "doc",
                "1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);

        client.index(request, RequestOptions.DEFAULT);


    }

    @Test
    public void testGet() throws IOException {

        GetRequest request = new GetRequest("posts", "doc", "1");


        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        System.out.println(response);

    }

    @Test
    public void testExists() throws IOException {
        GetRequest request1 = new GetRequest("posts", "doc", "1");
        //GetRequest request2 = new GetRequest("post2", "doc", "1");

        boolean exists = client.exists(request1, RequestOptions.DEFAULT);

        System.out.println(exists);

    }

    @Test
    public void testUpdate() throws IOException {
        GetRequest request1 = new GetRequest("posts", "doc", "1");

        GetResponse response1 = client.get(request1, RequestOptions.DEFAULT);

        System.out.println(response1.getVersion());

        System.out.println("================================================");

        UpdateRequest request = new UpdateRequest("posts", "doc", "1");

        String jsonString = "{" +
                "\"updated\":\"2017-01-01\"," +
                "\"reason\":\"daily update\"" +
                "}";
        request.doc(jsonString, XContentType.JSON);

        UpdateResponse response2 = client.update(request, RequestOptions.DEFAULT);

        System.out.println(response2.getVersion());

    }

    @Test
    public void testMultiGet() throws IOException {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item(
                "index",
                "type",
                "example_id"));
        request.add(new MultiGetRequest.Item("index", "type", "another_id"));

        MultiGetResponse responses = client.mget(request, RequestOptions.DEFAULT);

        responses.forEach(response ->
                System.out.println(response)
        );

    }

    @Test
    public void testSearchAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(20);
        builder.query(QueryBuilders.matchAllQuery());
        //searchRequest.indices("posts");
        searchRequest.source(builder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response);


    }






}
