package com.bridge.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridge.api.dto.NoteDto;
import com.bridge.api.service.NoteService;
import com.bridge.api.service.Service;

@RestController
@RequestMapping("/note")
public class NoteController {
		@Autowired
		NoteService service;
		
		
		@PostMapping("/create")
		public String addNote(@RequestBody NoteDto noteDto,@RequestParam String token) {
			service.createNote(noteDto, token);
			return "Note Created Successfully";
		}
		
		@PostMapping("/update")
		public String upadateNote(@RequestBody NoteDto noteDto,@RequestParam String noteId,@RequestParam String token) {
			
			return service.updateNote(noteDto,noteId,token);
		}
		
		@PostMapping("/delete")
		public String deleteNote(@RequestParam String noteId) {
			System.out.println("Shubham ujhhiuhi"+noteId);
			return service.delete(noteId);
		}
		@PostMapping("/retrive")
		public String retrive(@RequestParam String noteId) {
			return service.retrive(noteId);
		}
		@PostMapping("/pin")
		public String pinUnpined(@RequestParam String noteId,@RequestParam String token) {
			return service.isPined(noteId, token);
		}
		@PostMapping("/archive")
		public String archive(@RequestParam String noteId,@RequestParam String token) {
			return service.isArchive(noteId, token);
		}
		@PostMapping("/trash")
		public String trash(@RequestParam String noteId,@RequestParam String token) {
			return service.isTrash(noteId, token);
		}
}
