package com.ebsco.shake.bake.service.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true)
public class CopyData {
	
	 private String result;
     private String comment;

     public CopyData() {
     }

     public CopyData(String result, String comment) {
             this.result = result;
             this.comment = comment;
     }

     public String getResult() {
             return result;
     }
     public void setResult(String result) {
             this.result = result;
     }
     public String getComment() {
             return comment;
     }
     public void setComment(String comment) {
             this.comment = comment;
     }

	@Override
	public String toString() {
		return "CopyData [result=" + result + ", comment=" + comment + "]";
	}


}
