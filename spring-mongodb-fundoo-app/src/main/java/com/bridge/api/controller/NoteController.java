package com.bridge.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridge.api.config.ElasticSearch;
import com.bridge.api.dto.NoteDto;
import com.bridge.api.elasticservice.ElasticService;
import com.bridge.api.service.NoteService;
import com.bridge.api.service.UserService;

@RestController
@RequestMapping("/notes")
public class NoteController {
		@Autowired
		NoteService service;
		
		@Autowired
		ElasticService elasticService;
		@PostMapping("/createNote")
		public String addNote(@RequestBody NoteDto noteDto,@RequestParam String token) throws IOException {
			service.createNote(noteDto, token);
			return "Note Created Successfully";
		}
		
		@PutMapping("/updateNote")
		public String upadateNote(@RequestBody NoteDto noteDto,@RequestParam String noteId,@RequestParam String token) throws Exception {
			
			return service.updateNote(noteDto,noteId,token);
		}
		
		@DeleteMapping
		public String deleteNote(@RequestParam String noteId) throws IOException {
			System.out.println("Shubham ujhhiuhi"+noteId);
			return service.delete(noteId);
		}
		@GetMapping("/{noteId}")
		public String retrive(@PathVariable String noteId) {
			return service.retrive(noteId);
		}
		
		@PutMapping("/pin")
		public String pinUnpined(@RequestParam String noteId,@RequestParam String token) {
			return service.isPined(noteId, token);
		}
		@PutMapping("/archive")
		public String archive(@RequestParam String noteId,@RequestParam String token) {
			return service.isArchive(noteId, token);
		}
		@PutMapping("/trash")
		public String trash(@RequestParam String noteId,@RequestParam String token) {
			return service.isTrash(noteId, token);
		}
		
		@PutMapping("/eSearchByTitle")
		public String searchByTitle(@RequestParam String title, @RequestParam String userId) throws IOException {
			return elasticService.searchByTitle(title, userId).toString();
		}
		
		@PutMapping("/eSearchById")
		public String findById(@RequestParam String noteId) throws Exception {
			return elasticService.findById(noteId).toString();
		}
		
		
}
