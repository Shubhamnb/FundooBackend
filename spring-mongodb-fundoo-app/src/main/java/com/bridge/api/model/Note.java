package com.bridge.api.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "note")
public class Note {
	@Id
	private String noteId;
	private String noteTitle;
	private String discription;
	private LocalDateTime updatedTime;
	private LocalDateTime currentTime;
	private boolean pin;
	private boolean archive;
	private boolean trash;
	private String userId;
	@DBRef
	private List<Label> labelList;
	
	
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
	public LocalDateTime getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(LocalDateTime currentTime) {
		this.currentTime = currentTime;
	}
	public boolean isPin() {
		return pin;
	}
	public void setPin(boolean pin) {
		this.pin = pin;
	}
	public boolean isArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public boolean isTrash() {
		return trash;
	}
	public void setTrash(boolean trash) {
		this.trash = trash;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<Label> getLabelList() {
		return labelList;
	}
	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}
	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", noteTitle=" + noteTitle + ", discription=" + discription + ", updatedTime="
				+ updatedTime + ", currentTime=" + currentTime + ", pin=" + pin + ", archive=" + archive + ", trash="
				+ trash + ", userId=" + userId + ", labelList=" + labelList + "]";
	}
	
	
	
}
