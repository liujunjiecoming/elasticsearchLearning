package com.jj.elasticsearch.search_apis;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @ClassName SearchScrollApiEsTest
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午8:20
 * @Version 1.0
 **/
public class SearchScrollApiEsTest {

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
    public void initSearchScoll() throws IOException {
        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery("title", "Elasticsearch"));
        searchSourceBuilder.size(5);

        searchRequest.scroll(TimeValue.timeValueSeconds(60L));
        searchRequest.scroll("60s");

        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        System.out.println(scrollId);
        System.out.println(searchResponse.getHits().getHits());
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit);
            String json = hit.getSourceAsString();
            System.out.println(json);

        }

    }

    @Test
    public void fullExample() throws IOException {
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = new SearchRequest("posts");
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery("title", "Elasticsearch"));
        searchRequest.source(searchSourceBuilder);

        //1,Initialize the search context by sending the initial SearchRequest
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        //2,Retrieve all the search hits by calling the Search Scroll api in a loop until no documents are returned
        while (searchHits != null && searchHits.length > 0) {

            //3,Create a new SearchScrollRequest holding the last returned scroll identifier and the scroll interval
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
        }

        //4,Clear the scroll context once the scroll is completed
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        boolean succeeded = clearScrollResponse.isSucceeded();
        System.out.println(succeeded);

    }


}
