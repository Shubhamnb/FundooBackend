package com.bridge.api.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridge.api.dto.LabelDto;
import com.bridge.api.dto.NoteDto;
import com.bridge.api.elasticservice.ElasticService;
import com.bridge.api.model.Label;
import com.bridge.api.model.Note;
import com.bridge.api.model.User;
import com.bridge.api.mongo.reposetory.LabelRepository;
import com.bridge.api.mongo.reposetory.NoteRepository;
import com.bridge.api.mongo.reposetory.UserRepository;
import com.bridge.api.response.Response;
import com.bridge.api.util.StatusHelper;
import com.bridge.api.util.UserToken;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LabelRepository labelRepository;
	
	
	@Autowired
	ModelMapper mapper;

	@Autowired
	MailService mailService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserToken userToken;

	@Autowired
	NoteRepository noteRepository;

	
	@Autowired
	private Environment environment;
	
	
	  @Autowired 
	  ElasticService elasticService;
	 

	
	/* Method for Save a note in Mongod */
	@Override
	public Response createNote(NoteDto noteDto, String token) throws IOException {
		String userId = userToken.tokenVerify(token);
		
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			
			Note note = mapper.map(noteDto, Note.class);
			note.setCurrentTime(LocalDateTime.now());
			note.setUpdatedTime(LocalDateTime.now());
			note.setUserId(userId);
			Note noteElastic = noteRepository.save(note);
			elasticService.createNote(noteElastic); 
			System.out.println(userId);
			
			
			List<Note> noteList = user.get().getNotes();
			if (noteList != null && !noteList.contains(note)) {
			 	noteList.add(note);
				user.get().setNotes(noteList);
				userRepository.save(user.get());
				System.out.println("=====Shubham bohari=====List");
			} else {
				List<Note> list = new ArrayList<Note>();
				list.add(note);
				user.get().setNotes(list);
				userRepository.save(user.get());
			}
			
			
			Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.createdSuccessfull"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}//End IF
		else {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.empty"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
	}

	@Override
	public Response updateNote(NoteDto noteDto, String noteId, String token) throws Exception {
		String userId = userToken.tokenVerify(token);

		Note note = noteRepository.findByNoteIdAndUserId(noteId, userId);
		System.out.println(note);
		if (note != null) {
			note.setNoteTitle(noteDto.getNoteTitle());
			note.setDiscription(noteDto.getDiscription());
			note.setUpdatedTime(LocalDateTime.now());
			note.setCurrentTime(LocalDateTime.now());
			
			
		//	noteRepository.deleteById(noteId);
			Note noteCheck = noteRepository.save(note);
			
			
			elasticService.upDateNote(noteCheck);
			
			
			if (noteCheck != null) {
				Response response = StatusHelper.statusInfo(environment.getProperty("status.notes.updated"),
						Integer.parseInt(environment.getProperty("status.success.code")));
				return response;
			}
			else {
				Response response = StatusHelper.statusInfo(environment.getProperty("status.note.failedUpdate"),
						Integer.parseInt(environment.getProperty("status.error.code")));
				return response;
			}
		} else {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.failedUpdate"),
					Integer.parseInt(environment.getProperty("status.error.code")));
			return response;
		}
	}

	@Override
	public Response delete(String noteId) throws IOException {
		Optional<Note> note = noteRepository.findById(noteId);
		System.out.println(note);
		if (note.isPresent()) {
			Optional<User> user = userRepository.findById(note.get().getUserId());
			if (user.isPresent()) {
				System.out.println(user);
				List<Note> noteList = user.get().getNotes();
				System.out.println(noteList + " NoteList");

				if (noteList != null) {
					System.out.println(noteList + " NoteList");
					Iterator it = noteList.iterator();
					while (it.hasNext()) {
						Note userNote = (Note) it.next();
						if (userNote.getNoteTitle().equals(note.get().getNoteTitle())) {
							noteList.remove(userNote);
							break;
						}
					}
					user.get().setNotes(noteList);
					userRepository.save(user.get());
				}
				noteRepository.deleteById(noteId);
				elasticService.deleteNote(noteId);
				Response response = StatusHelper.statusInfo(environment.getProperty("status.note.delete"),
						Integer.parseInt(environment.getProperty("status.success.code")));
				return response;
			} else {
				Response response = StatusHelper.statusInfo(environment.getProperty("status.note.notdeleted"),
						Integer.parseInt(environment.getProperty("status.error.code")));
				return response;
			}

		} else {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.note.notdeleted"),
					Integer.parseInt(environment.getProperty("status.error.code")));
			return response;
		}

	}

	@Override
	public List<Note> retrive(String token) {
		String userId = userToken.tokenVerify(token);
		return noteRepository.findByUserId(userId);
	}

	@Override
	public String isPined(String noteId, String token) {
		String userId = userToken.tokenVerify(token);
		Optional<Note> note = noteRepository.findById(noteId);
		if (note.isPresent()) {
			if(note.get().isPin()) {
				note.get().setPin(false);
			}else {
				note.get().setPin(true);
			}
			noteRepository.save(note.get());
		}
		return "pin state changed";
	}

	@Override
	public Response isArchive(String noteId, String token) {
		String userId = userToken.tokenVerify(token);
		Note note = noteRepository.findByNoteIdAndUserId(noteId, userId);
		if (note != null) {
			if(note.isArchive()) {
				note.setArchive(false);
			}else {
				note.setArchive(true);
			}
			noteRepository.save(note);
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.archieved"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public String isTrash(String noteId, String token) {
		String userId = userToken.tokenVerify(token);
		Optional<Note> note = noteRepository.findById(noteId);
		if (note.isPresent()) {
			if(note.get().isTrash()) {
				note.get().setTrash(false);
			}else {
				note.get().setTrash(true);
			}
			noteRepository.save(note.get());
			
		}
		return "pin state changed";
	}

	@Override
	public Response createLabel(LabelDto labelDto, String token) {
		String userId = userToken.tokenVerify(token);
		Label label = mapper.map(labelDto, Label.class);
		label.setUserId(userId);
		Label label1 = labelRepository.save(label);
		
		if(label1 != null) {
			Response response = StatusHelper.statusInfo(environment.getProperty("status.label.created"),
					Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			Response response = StatusHelper.statusInfo("Label not created",
					Integer.parseInt(environment.getProperty("status.error.code")));
			return response;
		}
	}

	@Override
	public Response updateLabel(LabelDto labelDto,String labelId, String token) {
		String userId = userToken.tokenVerify(token);
		Optional<Label> label = labelRepository.findByIdAndUserId(labelId, userId);
		if(label.isPresent()) {
				label.get().setLabelName(labelDto.getLabelName());
				if(null != labelRepository.save(label.get())) {
					Response response = StatusHelper.statusInfo(environment.getProperty("status.label.updated"),
							Integer.parseInt(environment.getProperty("status.success.code")));
					return response;
				}else {
					Response response = StatusHelper.statusInfo("status.label.not.updated",
							Integer.parseInt(environment.getProperty("status.error.code")));
					return response;
				}
			}else {
				Response response = StatusHelper.statusInfo("status.label.not.updated",
						Integer.parseInt(environment.getProperty("status.error.code")));
				return response;
			}	
	}
	

	@Override
	
	public String deleteLabel(String labelId, String token) {
		String userId = userToken.tokenVerify(token);
		labelRepository.deleteById(labelId);
		Optional<Label> label = labelRepository.findById(labelId);
		if (label.isEmpty())
			return "Label Deleted";
		else
			return "Some problem accurs in label deletion";
	}
	 

	@Override
	public List<Label> retriveLabel(String token) {
		String userId = userToken.tokenVerify(token);
		Optional<User> user=userRepository.findById(userId);
		if (user.isPresent()) {
			List<Label> label = labelRepository.findAll();
			System.out.println(label);
			if (label != null)
				return label;
		}
		return null;
	}

	@Override
	public Response addLabelToNote(String labelId,String noteId,String token) {
		
		Optional<Note> note = noteRepository.findById(noteId);
		
		Optional<Label> label = labelRepository.findById(labelId);
		
		
		System.out.println(note.get() +"             ");
		if(note.isPresent() && label.isPresent()) {
			List<Label> labelList = note.get().getLabelList();
		
				if (labelList != null && !labelList.contains(label)) {
					labelList.add(label.get());
					note.get().setLabelList(labelList);
				} else {
					List<Label> newLabelList = new ArrayList<Label>();
					newLabelList.add(label.get());
					note.get().setLabelList(newLabelList);
				}
				noteRepository.save(note.get());
			
				Response response = StatusHelper.statusInfo(environment.getProperty("status.label.addedtonote"),
						Integer.parseInt(environment.getProperty("status.success.code")));
				return response;
		
		}else {
			System.out.println("Label SHUBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
			Response response = StatusHelper.statusInfo(environment.getProperty("status.label.error.addedtonote"),
					Integer.parseInt(environment.getProperty("status.error.code")));
			return response;
		}
	}

	@Override
	public String removeLabelFromNote(String labelId, String noteId) {
		Optional<Note> note  = noteRepository.findById(noteId);
		if(note.isPresent()) {
			List<Label> labelList = note.get().getLabelList();
			if(labelList != null) {
				Iterator<Label> it = labelList.iterator();
				while(it.hasNext()) {
					Label label = it.next();
					if(label.getId().equals(labelId)) {
						labelList.remove(label);
						note.get().setLabelList(labelList);
						noteRepository.save(note.get());
						return "Label Removed";
					}
				}
			}else {
				return "Label Not present";
			}
		}
		return "Label Not present";
	}

	@Override
	public List<Label> getLabelOfNote(String noteId, String token) {
		String userId = userToken.tokenVerify(token);
		Optional<User> isUser = userRepository.findById(userId);

		if (isUser.isPresent()) {
			Optional<Note> isNote = noteRepository.findById(noteId);
			return isNote.get().getLabelList();

		}
		return null;

	}

}
