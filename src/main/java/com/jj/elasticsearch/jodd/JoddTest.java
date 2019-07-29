package com.jj.elasticsearch.jodd;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.junit.Test;

/**
 * @ClassName JoddTest
 * @Description TODO
 * @Author JJLiu
 * @Date 19-7-19 上午9:39
 * @Version 1.0
 **/
public class JoddTest {

    @Test
    public void test() {
        HttpRequest httpRequest = HttpRequest.post("http://192.168.2.132:8080/roi")
                                      .body(" {'taskNo':'RO123122344', 'asin':'B01CR1AA90', 'marketplace':'US', 'type':'ROI'}  ");

        HttpResponse response = httpRequest.send();

        System.out.println("==========================");
        System.out.println(response.bodyText());


    }




}
