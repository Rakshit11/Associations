package com.manytomany.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.manytomany.model.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {
	static List<Model> findMOdelsByTagId(Long tagId) {
		return null;
	}

	Iterable<Model> findByTitleContaining(String title);


	List<Model> findByPublished(boolean b);

	List<Model> findModelsByTagsId(Long tagId);

}
