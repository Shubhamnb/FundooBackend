package com.bridge.api.mongo.reposetory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridge.api.model.Note;
@Repository
public interface NoteRepository extends MongoRepository<Note, String>{
	public Note findByNoteIdAndUserId(String noteId,String userId);
}
