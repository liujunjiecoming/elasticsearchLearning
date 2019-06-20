package com.jj.elasticsearch.search_apis;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SearchApiEsTest
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午4:06
 * @Version 1.0
 **/
public class SearchApiEsTest {

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
    public void matchAll() throws IOException {

        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println(response);

    }


    @Test
    public void useSearchSourceBuilder() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("posts");
        searchRequest.source(sourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response);

    }

    @Test
    public void buildingQueries() throws IOException {
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("user", "kim");
        //模糊查询
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        matchQueryBuilder.prefixLength(0);
        matchQueryBuilder.maxExpansions(10);

//        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
//                .fuzziness(Fuzziness.AUTO)
//                .prefixLength(3)
//                .maxExpansions(10);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(matchQueryBuilder);

        SearchRequest request = new SearchRequest();
        request.indices("posts");
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }

    @Test
    public void sort() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //sort by _score
        builder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //sort by _id
        builder.sort(new FieldSortBuilder("_uid").order(SortOrder.ASC));
        //turn off _source
        builder.fetchSource(false);


    }
































}
