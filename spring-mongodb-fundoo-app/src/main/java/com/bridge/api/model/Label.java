package com.bridge.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "label")
public class Label {
	@Id
	private String id;
	private String labelName;
	private String userId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	@Override
	public String toString() {
		return "Label [id=" + id + ", labelName=" + labelName + ", userId=" + userId + "]";
	}
	
	
}
