package com.note.micro.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.note.micro.model.Note;


@Repository
public interface NoteRepository extends MongoRepository<Note, String>{
	public Note findByNoteIdAndUserId(String noteId,String userId);
	public List<Note> findByUserId(String userId);
}
