package com.manytomany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manytomany.model.Tag;

public interface TagRepository extends JpaRepository<Tag,Long> {

	List<Tag> findTagsByModelsId(Long modelId);
	
}
