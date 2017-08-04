package com.liu.dao;

import java.util.List;

import com.liu.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void save(Category c)throws Exception;

}
