package com.bridge.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridge.api.dto.LabelDto;
import com.bridge.api.model.Label;
import com.bridge.api.response.Response;
import com.bridge.api.service.NoteService;


@RestController
@RequestMapping("/label")
@CrossOrigin(origins = "*",allowedHeaders = "*",exposedHeaders= {"token"})
public class LabelController {
	
	@Autowired
	NoteService service;
	
	@PostMapping("/create")
	public ResponseEntity<Response> create(@RequestBody LabelDto labelDto,@RequestHeader String token) {
		
		return new ResponseEntity<Response>(service.createLabel(labelDto,token),HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Response> update(@RequestBody LabelDto labelDto,@RequestParam String labelId,@RequestHeader String token) {
		
	
		return new ResponseEntity<Response>(service.updateLabel(labelDto,labelId,token),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public String delete(@RequestParam String labelId, @RequestParam String token) {
		return service.deleteLabel(labelId, token);
	}
	 
	@GetMapping("/retrive")
	public List<Label> retriveAll(@RequestHeader String token) {
		return service.retriveLabel(token);
	}
	@PostMapping("/addLabelToNotes")
	public ResponseEntity<Response> addLabelToNote(@RequestParam String labelId, @RequestParam String noteId,@RequestHeader String token) {
		 
		return new ResponseEntity<Response>(service.addLabelToNote(labelId, noteId,token),HttpStatus.OK);
	}

	@PostMapping("/removeLabelFromNote")
	public String removeLabelFromNote(@RequestParam String labelId, @RequestParam String noteId) {
		return service.removeLabelFromNote(labelId, noteId);
		
	}

}


