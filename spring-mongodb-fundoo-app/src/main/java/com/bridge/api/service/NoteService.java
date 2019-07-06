package com.bridge.api.service;

import java.io.IOException;

import com.bridge.api.dto.LabelDto;
import com.bridge.api.dto.NoteDto;

public interface NoteService{
	void createNote(NoteDto noteDto, String token)throws IOException;

	String updateNote(NoteDto noteDto, String noteId, String token)throws Exception;

	String delete(String noteId)throws IOException;

	String retrive(String noteId);

	String isPined(String noteId, String token);

	String isArchive(String noteId, String token);

	String isTrash(String noteId, String token);

	String createLabel(LabelDto labelDto, String token);

	String updateLabel(LabelDto labelDto, String token);

	String deleteLabel(String labelId, String token);

	String retriveLabel(String labelId, String token);

	String addLabelToNote(String labelId, String noteId);

	String removeLabelFromNote(String labelId, String noteId);

}
