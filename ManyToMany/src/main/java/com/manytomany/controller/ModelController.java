package com.manytomany.controller;

import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeNotFoundException;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manytomany.model.Model;
import com.manytomany.repository.ModelRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")


public class ModelController {

	@Autowired
	ModelRepository modelRepository;
	@GetMapping("/models")
	public ResponseEntity<List<Model>> getAllModels(@RequestParam(required = false) String title){
		List<Model> models = new ArrayList<Model>();
		if(title==null) 
			modelRepository.findAll().forEach(models::add);
		else
			modelRepository.findByTitleContaining(title).forEach(models::add);
		 if (models.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }
		    return new ResponseEntity<>(models, HttpStatus.OK);
		  }
		  @GetMapping("/models/{id}")
		  public ResponseEntity<Model> getTutorialById(@PathVariable("id") long id) throws AttributeNotFoundException {
		    Model tutorial = modelRepository.findById(id)
		        .orElseThrow(() -> new AttributeNotFoundException("Not found Tutorial with id = " + id));
		    return new ResponseEntity<>(tutorial, HttpStatus.OK);
		  }
		  @PostMapping("/models")
		  public ResponseEntity<Model> createTutorial(@RequestBody Model model) {
		    Model _model = modelRepository.save(new Model(model.getTitle(), model.getDescription(), true));
		    return new ResponseEntity<>(model, HttpStatus.CREATED);
		  }
		  @PutMapping("/models/{id}")
		  public ResponseEntity<Model> updateTutorial(@PathVariable("id") long id, @RequestBody Model model) throws AttributeNotFoundException {
		    Model _model = modelRepository.findById(id)
		        .orElseThrow(() -> new AttributeNotFoundException("Not found Model with id = " + id));
		    model.setTitle(model.getTitle());
		    _model.setDescription(model.getDescription());
		    _model.setPublished(model.isPublished());
		    
		    return new ResponseEntity<>(modelRepository.save(_model), HttpStatus.OK);
		  }
		  @DeleteMapping("/models/{id}")
		  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		    modelRepository.deleteById(id);
		    
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		  }
		  @DeleteMapping("/tutorials")
		  public ResponseEntity<HttpStatus> deleteAllTutorials() {
		    modelRepository.deleteAll();
		    
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		  }
		  @GetMapping("/tutorials/published")
		  public ResponseEntity<List<Model>> findByPublished() {
		    List<Model> models = modelRepository.findByPublished(true);
		    if (models.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }
		    
		    return new ResponseEntity<>(models, HttpStatus.OK);
		  }
		
	}

