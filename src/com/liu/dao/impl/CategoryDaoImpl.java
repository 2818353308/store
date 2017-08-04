package com.liu.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.liu.dao.CategoryDao;
import com.liu.domain.Category;
import com.liu.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {

	/**
	 * ��ѯ���з���
	 */
	public List<Category> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

	/**
	 * ��ӷ���
	 */
	public void save(Category c) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into category value (?,?);";
		qr.update(sql,c.getCid(),c.getCname());
	}
}
