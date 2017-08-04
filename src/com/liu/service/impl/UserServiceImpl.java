package com.liu.service.impl;

import com.liu.constant.Constant;
import com.liu.dao.UserDao;
import com.liu.domain.User;
import com.liu.service.UserService;
//import com.liu.utils.MailUtils;
import com.liu.utils.BeanFactory;

public class UserServiceImpl implements UserService{

	/**
	 * �û�ע��
	 * @throws Exception 
	 */
	public void regist(User user) throws Exception {
		//1.����dao���ע�� 
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		
		ud.save(user);
		//2.���ͼ����ʼ�
		String emailMsg="��ϲ"+user.getName()+":��Ϊ�����̳ǵ�һԱ,<a href='http://localhost/store/user?method=active&code="+user.getCode()+"'>��˼���</a>";
		System.out.println(emailMsg);
		//		MailUtils.sendMail(user.getEmail(), emailMsg);
	}

	/**
	 * �û�����
	 */
	public User active(String code) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		//ͨ��code��ȡ�û�
		User user = ud.getByCode(code);
		//1.1ͨ����֤��û���ҵ��û�
		if(user == null){
			return null;
		}
		//2.��ȡ�����޸��û�
		user.setState(Constant.USER_IS_ACTIVE);
		user.setCode(null);
		ud.update(user);
		return user;
	}

	/**
	 * �û���¼
	 * @throws Exception 
	 */
	public User login(String username, String password) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		return ud.getByUsernameAndPwd(username,password);
	}

}
