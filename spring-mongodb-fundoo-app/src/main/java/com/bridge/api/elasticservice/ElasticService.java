package com.bridge.api.elasticservice;

import java.io.IOException;
import java.util.List;



import com.bridge.api.model.Note;


public interface ElasticService {
	
	String createNote(Note note) throws IOException;

	public String deleteNote(String id) throws IOException;

	public Note findById(String id) throws Exception;

	public String upDateNote(Note note) throws Exception;

	public List<Note> searchByTitle(String title, String userId) throws IOException;
	 
}
