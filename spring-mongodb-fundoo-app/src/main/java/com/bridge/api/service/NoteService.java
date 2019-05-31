package com.bridge.api.service;

import com.bridge.api.dto.LabelDto;
import com.bridge.api.dto.NoteDto;

public interface NoteService {
	void createNote(NoteDto noteDto, String token);

	String updateNote(NoteDto noteDto, String noteId, String token);

	String delete(String noteId);

	String retrive(String noteId);

	String isPined(String noteId, String token);

	String isArchive(String noteId, String token);

	String isTrash(String noteId, String token);

	String createLabel(LabelDto labelDto, String token);

	String updateLabel(LabelDto labelDto, String token);

	String deleteLabel(String labelId, String token);

	String retriveLabel(String labelId, String token);

	String addLabelToNote(LabelDto labelDto, String noteId);

	String removeLabelFromNote(String labelId, String noteId);

	
}
