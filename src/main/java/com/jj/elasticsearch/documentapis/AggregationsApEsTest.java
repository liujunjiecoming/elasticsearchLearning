package com.jj.elasticsearch.documentapis;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName AggregationsApEsTest
 * @Description 聚合查询
 * @Author liujunjie
 * @Date 19-6-20 下午5:00
 * @Version 1.0
 **/
public class AggregationsApEsTest {

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


    @Test
    public void testTerms() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        TermsAggregationBuilder aggBuilder = AggregationBuilders.terms("click_count").field("clicks");


        //aggBuilder.subAggregation(AggregationBuilders.avg("")).field("");
        builder.aggregation(aggBuilder);
        builder.fetchSource(false);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("group_report");
        searchRequest.source(builder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println(response);

        Aggregations aggregations = response.getAggregations();
        System.out.println(aggregations);
        Terms clickCountAggregation = aggregations.get("click_count");
        System.out.println(clickCountAggregation);
        Terms.Bucket elasticBucket = clickCountAggregation.getBucketByKey("key");
        System.out.println(elasticBucket);

        Terms agg = response.getAggregations().get("click_count");
        System.out.println(agg.getBuckets().size());
        for (Terms.Bucket entry : agg.getBuckets()) {
            Long key = (Long) entry.getKey(); // bucket key
            long docCount = entry.getDocCount(); // Doc count
            System.out.println("key " + key + " doc_count " + docCount);

        }

    }

    @Test
    public void max() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(false);
        MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("max_clicks").field("clicks");
        searchSourceBuilder.aggregation(aggregationBuilder);

        SearchRequest request = new SearchRequest();
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }

    @Test
    public void min() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(false);
        MinAggregationBuilder aggregationBuilder = AggregationBuilders.min("min_clicks").field("clicks");
        searchSourceBuilder.aggregation(aggregationBuilder);

        SearchRequest request = new SearchRequest();
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);



        System.out.println(response);
    }




    @Test
    public void suggestions() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //Creates a new TermSuggestionBuilder for the user field and the text kmichy
        TermSuggestionBuilder termSuggestionBuilder = SuggestBuilders.termSuggestion("user").text("kimchy");
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        //Adds the suggestion builder and names it suggest_user
        suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
        builder.suggest(suggestBuilder);

        SearchRequest request = new SearchRequest();
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);


    }








}
