package com.ebsco.shake.bake.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.annotation.PostConstruct;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;


@Configuration
@EnableJms
public class DynamoDbConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbConfig.class);
	
	private AmazonDynamoDB  amazonDynamoDb;
    
	public DynamoDbConfig(@Value("${cloud.aws.credentials.accessKey}") String awsAccessKey
            		,@Value("${cloud.aws.credentials.secretKey}") String awsSecretKey
            		,@Value("${cloud.aws.region.static}") String awsRegion
            		,@Value("${cloud.aws.dynamo.end.point}") String dynamoEndPoint
            		,@Value("${cloud.aws.credentials.token}") String token
            		) {
		
		 Regions region = Regions.fromName(awsRegion);
		 
		 BasicSessionCredentials cred = new BasicSessionCredentials(awsAccessKey, awsSecretKey, token);
		 
		 amazonDynamoDb = AmazonDynamoDBClientBuilder.standard()
				 //.withCredentials(new AWSStaticCredentialsProvider( new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
				 .withCredentials(new AWSStaticCredentialsProvider( cred))
                 .withRegion(awsRegion)
                 .build();
	}
    
    @Bean
    public DynamoDBMapper mapper() {
    	return new DynamoDBMapper(amazonDynamoDb);
    }
   
}
