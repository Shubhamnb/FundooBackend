package com.bridge.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestHeader;

import com.bridge.api.dto.LabelDto;
import com.bridge.api.dto.NoteDto;
import com.bridge.api.model.Label;
import com.bridge.api.model.Note;
import com.bridge.api.response.Response;

public interface NoteService{
	Response createNote(NoteDto noteDto, String token)throws IOException;

	Response updateNote(NoteDto noteDto, String noteId, String token)throws Exception;

	Response delete(String noteId)throws IOException;

	List<Note> retrive(String token);

	String isPined(String noteId, String token);

	Response isArchive(String noteId, String token);

	String isTrash(String noteId, String token);

	Response createLabel(LabelDto labelDto, String token);

	Response updateLabel(LabelDto labelDto,String labelId, String token);

	String deleteLabel(String labelId, String token);

	List<Label> retriveLabel(String token);

	Response addLabelToNote(String labelId, String noteId,String token);

	String removeLabelFromNote(String labelId, String noteId);

	List<Label> getLabelOfNote(String noteId, String token);

}
