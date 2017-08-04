package com.liu.service;

import java.util.List;

import com.liu.domain.PageBean;
import com.liu.domain.Product;

public interface ProductService {

	List<Product> findHot() throws Exception;

	List<Product> findNew() throws Exception;

	Product getById(String pid) throws Exception;

	PageBean<Product> findByPage(int pageNumber, int pageSize, String cid)throws Exception;

	List<Product> findAll()throws Exception;

	void save(Product p)throws Exception;

	void delete(String pid)throws Exception;

}
