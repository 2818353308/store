package com.liu.service;

import java.util.List;

import com.liu.domain.Category;

public interface CategoryService {

	String findAll() throws Exception;

	String findAllFromRedis()throws Exception;

	List<Category> findList()throws Exception;

	void save(Category c)throws Exception;

}
