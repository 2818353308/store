package com.liu.service.impl;

import com.liu.constant.Constant;
import com.liu.dao.UserDao;
import com.liu.domain.User;
import com.liu.service.UserService;
//import com.liu.utils.MailUtils;
import com.liu.utils.BeanFactory;

public class UserServiceImpl implements UserService{

	/**
	 * 用户注册
	 * @throws Exception 
	 */
	public void regist(User user) throws Exception {
		//1.调用dao完成注册 
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		
		ud.save(user);
		//2.发送激活邮件
		String emailMsg="恭喜"+user.getName()+":成为我们商城的一员,<a href='http://localhost/store/user?method=active&code="+user.getCode()+"'>点此激活</a>";
		System.out.println(emailMsg);
		//		MailUtils.sendMail(user.getEmail(), emailMsg);
	}

	/**
	 * 用户激活
	 */
	public User active(String code) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		//通过code获取用户
		User user = ud.getByCode(code);
		//1.1通过验证码没有找到用户
		if(user == null){
			return null;
		}
		//2.获取到了修改用户
		user.setState(Constant.USER_IS_ACTIVE);
		user.setCode(null);
		ud.update(user);
		return user;
	}

	/**
	 * 用户登录
	 * @throws Exception 
	 */
	public User login(String username, String password) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		return ud.getByUsernameAndPwd(username,password);
	}

}
