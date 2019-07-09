package com.ebsco.shake.bake.consumer;


import javax.jms.JMSException;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.amazonaws.handlers.HandlerContextKey;
import com.ebsco.shake.bake.entity.Status;
import com.ebsco.shake.bake.repository.DynamoDbRepository;
import com.ebsco.shake.bake.service.pojo.CopyData;

@Component
public class AwsSqsListener {
	
	private static final Logger logger = LoggerFactory.getLogger(AwsSqsListener.class);
	
	
	@Value("${receiverapp.endpoint.url}")
	private String RECEIVER_APP_SERVER_URL;
	
	private final RestTemplate receiverAppRestTemplate;
	
	private final DynamoDbRepository dynamoDbRepo;
	
	public AwsSqsListener(@Autowired DynamoDbRepository repo, @Autowired RestTemplateBuilder restTemplateBuilder) {
		this.dynamoDbRepo = repo;
		this.receiverAppRestTemplate = new RestTemplate();
	}
	

    @JmsListener(destination = "${cloud.aws.sqs.queue}")
    public void processMessage(String message) throws JMSException {
    	logger.info("processMessage()");

	logger.info("\nIncoming Msg : " + message + "\n" );
    	
    	//logger.info("Which AWS Version Using : " + HandlerContextKey.class.getProtectionDomain().getCodeSource().getLocation().getPath() );
    	
	// Execute Our Receiver
        CopyData data = executeReceiverApp(message);

	// Copy Data into Dynamo
        Status status = new Status();
        status.setStatus(message);
        status.setDateAdded(LocalDateTime.now().toString("mm-DD-YYY HH:mm:ss") );
        insertIntoDynamo(status);
        
        
        logger.info("Data from Service : " + null);
    }
    
    private void insertIntoDynamo( Status status) {
    	
    	try {
        	dynamoDbRepo.insertIntoDynamoDB(status);
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
    	
    }
    
    private CopyData executeReceiverApp(String msg) {
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(RECEIVER_APP_SERVER_URL)
                .queryParam("contentset", "Delaney")
                .queryParam("content", "Dummy")
                .queryParam("message",  msg)
                .queryParam("secondsToPause", "10");
        String serverUrl = builder.toUriString();
        logger.info(String.format("Executing following URL : %s", serverUrl) );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        
        HttpEntity<CopyData> response = response = receiverAppRestTemplate.exchange(
                builder.toUriString(), 
                HttpMethod.GET, 
                entity, 
                CopyData.class);
    	
    	return response.getBody();
    }

}
