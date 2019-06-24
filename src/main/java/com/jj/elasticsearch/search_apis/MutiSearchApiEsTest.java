package com.jj.elasticsearch.search_apis;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName MutiSearchApi
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午2:38
 * @Version 1.0
 **/
public class MutiSearchApiEsTest {

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
    public void multiSearch() throws IOException {
        //1,Create an empty MultiSearchRequest.
        MultiSearchRequest request = new MultiSearchRequest();
        //2,Create an empty SearchRequest and populate it just like you would for a regular search
        SearchRequest firstSearchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
        firstSearchRequest.source(searchSourceBuilder);
        //3,Add the SearchRequest to the MultiSearchRequest.
        request.add(firstSearchRequest);
        //4,Build a second SearchRequest
        SearchRequest secondSearchRequest = new SearchRequest();
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "luca"));
        secondSearchRequest.source(searchSourceBuilder);
        //5,Add the SearchRequest to the MultiSearchRequest
        request.add(secondSearchRequest);

        MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
        System.out.println(response);

        MultiSearchResponse.Item firstResponse = response.getResponses()[0];

    }


}
