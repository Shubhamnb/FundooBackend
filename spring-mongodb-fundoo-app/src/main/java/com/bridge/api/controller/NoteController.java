package com.bridge.api.controller;

import java.io.IOException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridge.api.config.ElasticSearch;
import com.bridge.api.dto.NoteDto;
import com.bridge.api.elasticservice.ElasticService;
import com.bridge.api.model.Label;
import com.bridge.api.model.Note;
import com.bridge.api.response.Response;
import com.bridge.api.service.NoteService;
import com.bridge.api.service.UserService;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "*",allowedHeaders = "*",exposedHeaders= {"token"})
public class NoteController {
		@Autowired
		NoteService service;
		
		@Autowired
		ElasticService elasticService;
		@PostMapping("/createNote")
		public ResponseEntity<Response> addNote(@RequestBody NoteDto noteDto,@RequestHeader String token) throws IOException {
			System.out.println("Token ====== "+token);
			Response response = service.createNote(noteDto, token);
			return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
		
		@PutMapping("/updateNote")
		public ResponseEntity<Response> upadateNote(@RequestBody NoteDto noteDto,@RequestParam String noteId,@RequestHeader String token) throws Exception {
			Response response = service.updateNote(noteDto,noteId,token);
			return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
		
		@DeleteMapping
		public ResponseEntity<Response> deleteNote(@RequestParam String noteId,@RequestHeader String token) throws IOException {
			System.out.println("Shubham ujhhiuhi"+noteId);
			return new ResponseEntity<Response>(service.delete(noteId),HttpStatus.OK);
		}
		@GetMapping("/getAllNotes")
		public List<Note> retrive(@RequestHeader String token) {
			return service.retrive(token);
		}
		
		@GetMapping("/getNoteLabels")
		public List<Label> getNoteLabels(@RequestParam String noteId,@RequestHeader String token){
		List<Label> noteLabelList=service.getLabelOfNote(noteId,token);
		return noteLabelList;
		}
		
		@PutMapping("/pin")
		public String pinUnpined(@RequestParam String noteId,@RequestParam String token) {
			return service.isPined(noteId, token);
		}
		@PutMapping("/archive")
		public ResponseEntity<Response> archive(@RequestParam String noteId,@RequestHeader String token) {
			return new ResponseEntity<Response>(service.isArchive(noteId, token),HttpStatus.OK);
		}
		@PutMapping("/trash")
		public String trash(@RequestParam String noteId,@RequestParam String token) {
			return service.isTrash(noteId, token);
		}
		
		@PutMapping("/eSearchByTitle")
		public List<Note> searchByTitle(@RequestParam String title, @RequestParam String token) throws IOException {
			System.out.println("Shubham ="+elasticService.searchByTitle(title, token));
			return elasticService.searchByTitle(title, token);
		}
		
		@PutMapping("/eSearchById")
		public String findById(@RequestParam String noteId) throws Exception {
			return elasticService.findById(noteId).toString();
		}
		
		
}
