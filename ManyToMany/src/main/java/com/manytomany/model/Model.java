package com.manytomany.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "model")
public class Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="Title")
	private String title;
	@Column(name="Description")
	private String description;
	@Column(name="published")
	private boolean published;
	@ManyToMany(fetch = FetchType.LAZY,
			cascade= {
			CascadeType.PERSIST,
			CascadeType.MERGE
			})
	@JoinTable(name="Models_tags"
	,joinColumns= {@JoinColumn(name="Models_id")},
	inverseJoinColumns= {@JoinColumn(name="tag_id")})
	private Set<Tag> tags = new HashSet<>();
	public Model() {}
	public Model(String title, String description, boolean published) {
		this.title=title;
		this.description=description;
		this.published=published;
	}
	public Model(Object title2, Object description2, boolean b) {
	}
	public void addTag(Tag tag) {
		this.tags.add(tag);
		tag.getModels().add(this);
	}
	public void removeTag(long tagId) {
		Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
	
		if(tag!=null) {
			this.tags.remove(tag);
			tag.getModels().remove(this);
		}
	
	}
	public Object getTitle() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setTitle(Object title2) {
		// TODO Auto-generated method stub
		
	}
	public Object getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setDescription(Object description) {
		// TODO Auto-generated method stub
		
	}
	public Object isPublished() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setPublished(Object published) {
		// TODO Auto-generated method stub
		
	}

}
