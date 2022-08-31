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
import org.springframework.web.bind.annotation.RestController;

import com.manytomany.model.Model;
import com.manytomany.model.Tag;
import com.manytomany.repository.ModelRepository;
import com.manytomany.repository.TagRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class TagController {

	@Autowired
	  private ModelRepository modellRepository;
	  @Autowired
	  private TagRepository tagRepository;
	  @GetMapping("/tags")
	  public ResponseEntity<List<Tag>> getAllTags() {
	    List<Tag> tags = new ArrayList<Tag>();
	    tagRepository.findAll().forEach(tags::add);
	    if (tags.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(tags, HttpStatus.OK);
	  }
	  
	  @GetMapping("/models/{modelId}/tags")
	  public ResponseEntity<List<Tag>> getAllTagsByModelId(@PathVariable(value = "modelId") Long modelId) throws AttributeNotFoundException {
	    if (!modellRepository.existsById(modelId)) {
	      throw new AttributeNotFoundException("Not found Tutorial with id = " + modelId);
	    }
	    List<Tag> tags = tagRepository.findTagsByModelsId(modelId);
	    return new ResponseEntity<>(tags, HttpStatus.OK);
	  }
	  @GetMapping("/tags/{id}")
	  public ResponseEntity<Tag> getTagsById(@PathVariable(value = "id") Long id) throws AttributeNotFoundException {
	    Tag tag = tagRepository.findById(id)
	        .orElseThrow(() -> new AttributeNotFoundException("Not found Tag with id = " + id));
	    return new ResponseEntity<>(tag, HttpStatus.OK);
	  }
	  
	  @GetMapping("/tags/{tagId}/models")
	  public ResponseEntity<List<Model>> getAllTutorialsByTagId(@PathVariable(value = "tagId") Long tagId) throws AttributeNotFoundException {
	    if (!tagRepository.existsById(tagId)) {
	      throw new AttributeNotFoundException("Not found Tag  with id = " + tagId);
	    }
	    List<Model> models= modellRepository.findModelsByTagsId(tagId);
	    return new ResponseEntity<>(models, HttpStatus.OK);
	  }
	  @PostMapping("/models/{modelId}/tags")
	  public ResponseEntity<Tag> addTag(@PathVariable(value = "tutorialId") Long modelId, @RequestBody Tag tagRequest) throws AttributeNotFoundException {
	    Tag tag = modellRepository.findById(modelId).map(model -> {
	      long tagId = tagRequest.getId();
	      
	      // tag is existed
	      if (tagId != 0L) {
	        Tag _tag = null;
			try {
				_tag = tagRepository.findById(tagId)
				    .orElseThrow(() -> new AttributeNotFoundException("Not found Tag with id = " + tagId));
			} catch (AttributeNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        model.addTag(_tag);
	        modellRepository.save(model);
	        return _tag;
	      }
	      
	      // add and create new Tag
	      model.addTag(tagRequest);
	      return tagRepository.save(tagRequest);
	    }).orElseThrow(() -> new AttributeNotFoundException("Not found Tutorial with id = " + modelId));
	    return new ResponseEntity<>(tag, HttpStatus.CREATED);
	  }
	  @PutMapping("/tags/{id}")
	  public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest) throws AttributeNotFoundException {
	    Tag tag = tagRepository.findById(id)
	        .orElseThrow(() -> new AttributeNotFoundException("TagId " + id + "not found"));
	    tag.setName(tagRequest.getName());
	    return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
	  }
	 
	  @DeleteMapping("/models/{modelId}/tags/{tagId}")
	  public ResponseEntity<HttpStatus> deleteTagFromModel(@PathVariable(value = "modelId") Long modelId, @PathVariable(value = "tagId") Long tagId) throws AttributeNotFoundException {
	    Model model = modellRepository.findById(modelId)
	        .orElseThrow(() -> new AttributeNotFoundException("Not found Tutorial with id = " + modelId));
	    
	    model.removeTag(tagId);
	    modellRepository.save(model);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	  
	  @DeleteMapping("/tags/{id}")
	  public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
	    tagRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
}
