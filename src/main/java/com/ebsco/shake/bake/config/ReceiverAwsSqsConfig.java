package com.ebsco.shake.bake.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;


import javax.annotation.PostConstruct;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.util.ErrorHandler;

@Configuration
@EnableJms
public class ReceiverAwsSqsConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverAwsSqsConfig.class);

    @PostConstruct
    public void init() {
        LOGGER.info("Started.");
    }
    
	
    private final SQSConnectionFactory connectionFactory;
    


    public ReceiverAwsSqsConfig(@Value("${cloud.aws.credentials.accessKey}") String awsAccessKey
            		, @Value("${cloud.aws.credentials.secretKey}") String awsSecretKey
            		, @Value("${cloud.aws.region.static}") String awsRegion
            		, @Value("${cloud.aws.credentials.token}") String token
    		) {
    	
    		BasicSessionCredentials cred = new BasicSessionCredentials(awsAccessKey, awsSecretKey, token);
    	
            Regions region = Regions.fromName(awsRegion);
    		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
    				.withRegion(region)
    				//.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
    				.withCredentials(new AWSStaticCredentialsProvider(cred))
    				.build();
    		connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(), sqs);
        }
    
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    	// Create JmsListener
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        // Use AWS queue connection factory to connect to AWS queues.
        factory.setConnectionFactory(connectionFactory);

        // ActiveMQ support transacted
        // factory.setSessionTransacted(true);
        // factory.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);

        // SQS does not support transacted
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("3-10");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        // Set error handler (optional)
        factory.setErrorHandler(defaultErrorHandler());

        // Set message converter (optional)
        // Will have error if converter cannot convert the message.
        //factory.setMessageConverter(jacksonJmsMessageConverter());
        
        return factory;
    }
    
    @Bean
    public ErrorHandler defaultErrorHandler() {
        return new ErrorHandler() {
            @Override
            public void handleError(Throwable throwable) {
                // print error...
                // send email and SMS...
                System.err.println(throwable.getMessage());
            }
        };
    }
    
    

    @Bean
    public JmsTemplate jmsTemplate() {
//    	JmsTemplate jmsTemplate = new JmsTemplate(this.connectionFactory);
//        jmsTemplate.setMessageConverter(messageConverter());
        return new JmsTemplate(this.connectionFactory);
    }

//    @Bean
//    public MessageConverter messageConverter() {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
//        builder.dateFormat(new ISO8601DateFormat());
//
//        org.springframework.jms.support.converter.MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
//
//        mappingJackson2MessageConverter.setObjectMapper(builder.build());
//        mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
//        //mappingJackson2MessageConverter.setTypeIdPropertyName("documentType");
//        return mappingJackson2MessageConverter;
//    }
}
