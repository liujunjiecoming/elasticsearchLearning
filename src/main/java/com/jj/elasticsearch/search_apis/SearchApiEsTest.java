package com.jj.elasticsearch.search_apis;

import com.jj.elasticsearch.entity.DayDO;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    private String hostname = "192.168.196.140";
    //private String hostname = "127.0.0.1";

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
    public void test1() throws IOException {

        WildcardQueryBuilder keywords = QueryBuilders.wildcardQuery("keywords", "*swimsuit*");

        SearchSourceBuilder builder1 = new SearchSourceBuilder();
        builder1.from(0);
        builder1.size(100);
        builder1.sort(new FieldSortBuilder("reportTime").order(SortOrder.DESC));

        StatsAggregationBuilder clicksAgg = AggregationBuilders.stats("clicksAgg").field("clicks");
        StatsAggregationBuilder searchesAgg = AggregationBuilders.stats("searchesAgg").field("searches");
        StatsAggregationBuilder salesAgg = AggregationBuilders.stats("salesAgg").field("ops");
        builder1.aggregation(clicksAgg);
        builder1.aggregation(searchesAgg);
        builder1.aggregation(salesAgg);

        String day = "2018-05-18";

        //获取当前月的第一天和最后一天
        DayDO currentMonth = getFirstAndLastDay(0, 1, 1, 0, day);
        System.out.println("当前月   :" + currentMonth);
        //上一个月的第一天和最后一天
        DayDO lastMonth = getFirstAndLastDay(-1, 1, 0, 0, day);
        System.out.println("上个月   :" + lastMonth);
        //上一年同月的第一天和最后一天
        DayDO lastYear = getLastYearFirstAndLastDay(day);
        System.out.println("上一年同月: " + lastYear);

        RangeQueryBuilder currentMonthDay = QueryBuilders.rangeQuery("reportTime")
                                                         .from(currentMonth.getFirstDay())
                                                         .to(currentMonth.getLastDay());

        RangeQueryBuilder lastMonthDay = QueryBuilders.rangeQuery("reportTime")
                                                      .from(lastMonth.getFirstDay())
                                                      .to(lastMonth.getLastDay());

        RangeQueryBuilder lastYearDay = QueryBuilders.rangeQuery("reportTime")
                                                     .from(lastYear.getFirstDay())
                                                     .to(lastYear.getLastDay());

        // String keyword = "levi 541 stretch jeans for men";
        // BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
        //                                              .must(QueryBuilders.rangeQuery("reportTime")
        //                                                                 .from(lastMonth.getFirstDay())
        //                                                                 .to(lastMonth.getLastDay()));
        // if (keyword != null) {
        //     queryBuilder.must(QueryBuilders.wildcardQuery("keywords", "*" + keyword + "*"));
        // }

        // MatchQueryBuilder queryBuilder = new MatchQueryBuilder("keywords", "levi 541 stretch jeans for men");
        // MatchQueryBuilder queryBuilder1 = new MatchQueryBuilder("reportTime", "2018-05-01");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                                                     .must(QueryBuilders.termQuery("reportTime", "2018-05-01"))
                                                     .must(QueryBuilders.termQuery("keywords", "levi 541 stretch jeans for men"));


        //构建查询
        builder1.query(queryBuilder);

        SearchRequest request = new SearchRequest();
        request.source(builder1);
        request.indices("a9");

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        Stats clicks = response.getAggregations().get("clicksAgg");
        System.out.println(nf.format(clicks.getSum()));
        Stats searches = response.getAggregations().get("searchesAgg");
        System.out.println(nf.format(searches.getSum()));
        Stats sales = response.getAggregations().get("salesAgg");
        System.out.println(nf.format(sales.getSum()));

        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            System.out.println(map.get("keywords"));

            System.out.println(hit);
        }

    }

    public static DayDO getFirstAndLastDay(int firstIndex,
                                           int secondIndex,
                                           int thirdIndex,
                                           int fourthIndex,
                                           String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(date));
        cal.add(Calendar.MONTH, firstIndex);
        cal.set(Calendar.DAY_OF_MONTH, secondIndex);
        String first = format.format(cal.getTime());
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.setTime(strToDate(date));
        ca.add(Calendar.MONTH, thirdIndex);
        ca.set(Calendar.DAY_OF_MONTH, fourthIndex);
        String last = format.format(ca.getTime());

        return new DayDO(first, last);
    }

    public static DayDO getLastYearFirstAndLastDay(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取上一年同一个月的第一天
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.setTime(strToDate(date));
        cal.add(Calendar.MONTH, -12);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal.getTime());
        //获取上一年同一个月的最后一天
        Calendar cale3 = Calendar.getInstance();
        cale3.setTime(strToDate(date));
        cale3.add(Calendar.MONTH, -11);
        cale3.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        String lastDay = format.format(cale3.getTime());

        return new DayDO(firstDay, lastDay);
    }

    public static Date strToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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

    @Test
    public void test2() throws IOException {

        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchQueryBuilder queryBuilder = new MatchQueryBuilder("reportTime", "2018-07-01");
        builder.query(queryBuilder);
        builder.from(0);
        builder.size(100);

        SearchRequest request = new SearchRequest();
        request.indices("a9");
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            System.out.println(map);
        }

    }

    @Test
    public void test3() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(88);
        list.add(4444);
        list.add(222);
        list.add(111);
        list.add(333);
        System.out.println(list);

        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.intValue() - o1.intValue();
            }

        });

        System.out.println(list);
    }


}
