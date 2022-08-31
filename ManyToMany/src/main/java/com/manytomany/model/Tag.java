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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tags")
public class Tag {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="name")
	private String name;
	@ManyToMany(fetch=FetchType.LAZY,
			cascade= {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			mappedBy = "tags")
	@JsonIgnore
	private Set<Model> models = new HashSet<>();
	public Tag() {
		
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public Set<Model> getModels(){
		return models;
	}
	public void setModels(Set<Model> models) {
		this.models=models;
	}

}
