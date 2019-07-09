package com.ebsco.shake.bake.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;


public class ReceiveAppMsg implements Serializable {

    private static final long serialVersionUID = -8013965441896177936L;

    String id;
    String content;
    String contentType;
    String folder;
    Date date;
    
    public ReceiveAppMsg() {
    }

    @JsonCreator
    public ReceiveAppMsg(@JsonProperty("id") String id, @JsonProperty("content") String content, @JsonProperty("date") Date date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static ReceiveAppMsg builder() {
		return new ReceiveAppMsg();
	}
	public ReceiveAppMsg id(String id) {
		setId(id);
		return this;
	}
	public ReceiveAppMsg withContent(String c ) {
		setContent(c);
		return this;
	}
	public ReceiveAppMsg withContentType(String c ) {
		setContentType(c);
		return this;
	}
	public ReceiveAppMsg withFolder(String c ) {
		setFolder(c);
		return this;
	}
	public ReceiveAppMsg date(Date d ) {
		setDate(d);
		return this;
	}



}
