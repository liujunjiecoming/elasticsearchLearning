package com.jj.elasticsearch.documentapis;

import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static java.util.Collections.singletonMap;

/**
 * @ClassName UpdateApiEsTest
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-20 下午3:32
 * @Version 1.0
 **/
public class UpdateApiEsTest {

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
    public void updateByScript() throws IOException {
        UpdateRequest request = new UpdateRequest(
                "posts",
                "doc",
                "1");

        Map<String, Object> parameters = singletonMap("count", 4);

        Script inline = new Script(ScriptType.INLINE, "painless",
                "ctx._source.field += params.count", parameters);
        request.script(inline);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }

    @Test
    public void updateByDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("posts", "doc", "1");
        String jsonString = "{" +
                "\"updated\":\"2018-03-21\"," +
                "\"reason\":\"daily update twice\"" +
                "}";
        request.doc(jsonString, XContentType.JSON);

        UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
        System.out.println(update);

    }



}
