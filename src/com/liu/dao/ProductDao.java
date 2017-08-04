package com.liu.dao;

import java.util.List;

import com.liu.domain.PageBean;
import com.liu.domain.Product;

public interface ProductDao {

	List<Product> findHot()throws Exception;

	List<Product> findNew()throws Exception;

	Product getById(String pid)throws Exception ;

	List<Product> findByPage(PageBean<Product> pb, String cid)throws Exception ;

	int getTotalRecord(String cid)throws Exception ;

	List<Product> findAll()throws Exception ;

	void save(Product p)throws Exception;

	void delete(String pid)throws Exception;

}
