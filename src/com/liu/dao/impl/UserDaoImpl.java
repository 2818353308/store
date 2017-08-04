package com.liu.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.liu.dao.UserDao;
import com.liu.domain.User;
import com.liu.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	/**
	 * �û�ע��
	 * @throws SQLException 
	 */
	public void save(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),
				user.getCode());
		
	}

	/**
	 * ͨ���������ȡ�û�
	 */
	public User getByCode(String code) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code = ? limit 1";
		return qr.query(sql, new BeanHandler<User>(User.class), code);
	}

	/**
	 * �����û�
	 */
	public void update(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update user set password = ?,sex = ?,state = ?,code = ? where uid = ?";
		qr.update(sql, user.getPassword(),user.getSex(),user.getState(),user.getCode(),user.getUid());
	}
	/**
	 * �û���¼
	 * @throws SQLException 
	 */
	public User getByUsernameAndPwd(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? limit 1";
		return qr.query(sql, new BeanHandler<User>(User.class), username,password);
	}

}
