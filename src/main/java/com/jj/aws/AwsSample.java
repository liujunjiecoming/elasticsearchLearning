package com.jj.aws;


import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.iot.model.DynamoDBAction;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.sts.model.Credentials;

/**
 * @ClassName AwsSample
 * @Description TODO
 * @Author JJLiu
 * @Date 19-10-8 上午11:35
 * @Version 1.0
 **/
public class AwsSample {

    public static void main(String[] args) {

        Credentials.Builder builder = Credentials.builder()
                                                 .accessKeyId("AKIAYBRJBEZMXGJLEN55")
                                                 .secretAccessKey("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")
                                                 .sessionToken("");




        // InvokeRequest.builder().functionName("")
        //              .payload()

        DynamoDbClient client = DynamoDbClient.builder()
                                              .region(Region.US_WEST_2)
                                              .credentialsProvider(ProfileCredentialsProvider.builder()
                                                                                             .profileName("myProfile")
                                                                                             .build())
                                              .build();

        // DynamoDbClient client = DynamoDbClient.create();

        client.close();



    }


}
