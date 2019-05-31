package com.bridge.api.dto;

public class LabelDto {
	
	
	private String id;
	private String labelName;
	
	
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
	@Override
	public String toString() {
		return "LabelDto [id=" + id + ", labelName=" + labelName + "]";
	}
	
	
}
