package com.bridge.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridge.api.dto.LabelDto;
import com.bridge.api.service.NoteService;


@RestController
@RequestMapping("/label")
public class LabelController {
	
	@Autowired
	NoteService service;
	
	@PostMapping("/create")
	public String create(@RequestBody LabelDto labelDto,@RequestParam String token) {
		
		return service.createLabel(labelDto,token);
	}
	
	@PostMapping("/update")
	public String update(@RequestBody LabelDto labelDto,@RequestParam String token) {
		
		return service.updateLabel(labelDto,token);
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam String labelId,@RequestParam String token) {
		
		return service.deleteLabel(labelId,token);
	}
	@PostMapping("/retrive")
	public String retrive(@RequestParam String labelId,@RequestParam String token) {
		return service.retriveLabel(labelId,token);
	}
	@PostMapping("/addLabelToNotes")
	public String addLabelToNote(@RequestBody LabelDto labelDto, @RequestParam String noteId) {
		return service.addLabelToNote(labelDto, noteId);
		
	}

	@PostMapping("/removeLabelFromNote")
	public String removeLabelFromNote(@RequestParam String labelId, @RequestParam String noteId) {
		return service.removeLabelFromNote(labelId, noteId);
		
	}

}


