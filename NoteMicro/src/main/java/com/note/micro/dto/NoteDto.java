package com.note.micro.dto;

public class NoteDto {
	private String noteTitle;
	private String discription;

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

	@Override
	public String toString() {
		return "NoteDto [noteTitle=" + noteTitle + ", discription=" + discription + "]";
	}

}
