package com.ebsco.shake.bake.entity;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "STATUS")
public class Status implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String statusId;
	private String status;
	private String dateAdded;
	
	@DynamoDBHashKey(attributeName = "statusId")
	@DynamoDBAutoGeneratedKey
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String id) {
		this.statusId = id;
	}
	
	@DynamoDBAttribute
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@DynamoDBRangeKey
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	
}
