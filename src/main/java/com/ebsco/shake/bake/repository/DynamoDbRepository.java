package com.ebsco.shake.bake.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.ebsco.shake.bake.entity.Status;


@Repository
public class DynamoDbRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbRepository.class);
	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void insertIntoDynamoDB(Status status) {
		mapper.save(status);
	}

}
