package com.jj.elasticsearch.company;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.RequestBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TestAgg
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-18 下午7:17
 * @Version 1.0
 **/
public class TestAgg {

    private RestHighLevelClient client = null;

    private String hostname = "192.168.196.140";

    private int port = 9200;

    @Before
    public void beforeClient() throws UnknownHostException {

        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, "http")
                ));


    }

    @Test
    public void testAgg() {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company")
                .field("company.keyword");
        aggregation.subAggregation(AggregationBuilders.avg("average_age")
                .field("age"));
        SearchSourceBuilder builder = searchSourceBuilder.aggregation(aggregation);
        System.out.println(builder);

    }

    @Test
    public void testTerm() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.size(5);

        TermsAggregationBuilder termsBuilder = AggregationBuilders.terms("clicks_count").field("clicks");
        //TermsAggregationBuilder termsBuilder = AggregationBuilders.terms("types").field("type");

        //DateRangeAggregationBuilder aggregationBuilder = AggregationBuilders.dateRange("reportTimes").field("reportTime");
        RangeAggregationBuilder aggregationBuilder = AggregationBuilders.range("click_count").field("clicks").addRange(5, 10);

        // MinAggregationBuilder minAgg = AggregationBuilders.min("MinClicks").field("clicks");

        //termsBuilder.subAggregation(minAgg);

        builder.aggregation(termsBuilder);

        SearchRequest request = new SearchRequest();
        request.indices("group_report", "system_log");
        request.types("doc");
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        Aggregations aggregation = response.getAggregations();
        List<Aggregation> aggregations = aggregation.asList();
        for (Aggregation agg : aggregations) {
            String type = agg.getType();
            System.out.println(type);
            if (type.equals(TermsAggregationBuilder.NAME)) {
                System.out.println("111");
                Terms.Bucket elasticBucket = ((Terms) agg).getBucketByKey("click_count");
                System.out.println(elasticBucket);
                long numberOfDocs = elasticBucket.getDocCount();
                System.out.println(numberOfDocs);
            }
        }


        //System.out.println(response.getAggregations().get("clicks_count"));

        //System.out.println(response);
//        Aggregations agg = response.getAggregations();
//        List<Aggregation> aggregations = agg.asList();
//        System.out.println(aggregations);
//        Range range = agg.get("click_count");
//        for (Range.Bucket entry : range.getBuckets()) {
//            String from = entry.getFromAsString();
//            String to = entry.getToAsString();
//            System.out.println("from " + from + " to " + to);
//        }


//        Terms agg = response.getAggregations().get("click_count");
//
//        System.out.println(agg.getBuckets().size());
//        for (Terms.Bucket entry : agg.getBuckets()) {
//            Long key = (Long) entry.getKey();
//            //Date date = (Date) entry.getKey();
//            long docCount = entry.getDocCount();
//            System.out.println("key " + key + " doc_count " + docCount);
////            System.out.println("date " + date + " doc_count " + docCount);


    }

    @Test
    public void test2() throws IOException {

        SearchRequest searchRequest = new SearchRequest("system_log");
        SearchSourceBuilder sBuilder = new SearchSourceBuilder();
        sBuilder.query(QueryBuilders.matchAllQuery());
        SearchRequest source = searchRequest.source(sBuilder);

        SearchResponse response = client.search(source, RequestOptions.DEFAULT);

        System.out.println(response);

    }

    @Test
    public void test3() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("user", "kimchy"));
        builder.from(0);
        builder.size(5);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest request = new SearchRequest();
        request.indices("posts");
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);


    }

    @Test
    public void test4() throws IOException {
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("user", "kimchy");
//        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
//        matchQueryBuilder.prefixLength(3);
//        matchQueryBuilder.maxExpansions(10);

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(3)
                .maxExpansions(10);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(matchQueryBuilder);

        SearchRequest request = new SearchRequest();
        request.indices("posts");
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);


    }

    @Test
    public void test5() throws IOException {
        SearchRequest searchRequest = new SearchRequest("group_report");
        SearchSourceBuilder sBuilder = new SearchSourceBuilder();
        sBuilder.fetchSource(false);
        sBuilder.query(QueryBuilders.matchAllQuery());
        SearchRequest source = searchRequest.source(sBuilder);

        SearchResponse response = client.search(source, RequestOptions.DEFAULT);

        System.out.println(response);


    }


}
















