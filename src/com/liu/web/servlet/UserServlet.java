package com.liu.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.liu.constant.Constant;
import com.liu.domain.User;
import com.liu.service.UserService;
import com.liu.service.impl.UserServiceImpl;
import com.liu.utils.UUIDUtils;
import com.liu.web.servlet.base.BaseServlet;

/**
 * 用户模块
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L; 
	
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath());
		return null;
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.获取用户名和密码
			String username = request.getParameter("username"); 
			String password = request.getParameter("password"); 
			//2.调用service完成登录  返回值：user
			UserService us = new UserServiceImpl();
			User user = us.login(username,password);
			//3.判断user 根据结果生成提示
			if(user == null){
				//用户名和密码不匹配
				request.setAttribute("msg", "用户名和密码不匹配");
				return "/jsp/login.jsp";
			}
			//若用户名不为空，继续判断是否激活
			if(Constant.USER_IS_ACTIVE!=user.getState()){
				//未激活
				request.setAttribute("msg","请先激活再登录");
				return "/jsp/msg.jsp";
			}
			//登录 保存用户登录状态
			request.getSession().setAttribute("user", user);
			//*******************记住用户名**************************
			//判断是否勾选了记住用户名
			if(Constant.SAVE_NAME.equals(request.getParameter("savenaem"))){
				Cookie c = new Cookie( "saveName", URLEncoder.encode(username, "utf-8"));
				c.setMaxAge(Integer.MAX_VALUE);
				c.setPath(request.getContextPath()+"/");
				response.addCookie(c);
			}
			//跳转到index.jsp
			response.sendRedirect(request.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","登录失败");
			return "/jsp/msg.jsp";
		}
		return null;
	}
	
	/**
	 * 跳转到登录页
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/login.jsp";
	}
	
	/**
	 * 用户激活
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.接受code
			String code = request.getParameter("code");
			//2.调用service完成激活 返回值；user
			UserService us = new UserServiceImpl();
			User user = us.active(code);
			
			//3.判断user生成不同的提示信息
			if(user == null){
				 //没有找到这个用户，激活失败
				request.setAttribute("msg", "激活失败，请重新激活或者重新注册！");
				return "/jsp/msg.jsp";
			}
			//激活成功
			request.setAttribute("msg", "激活成功，可以登录了");
		} catch (Exception e) {
			e.printStackTrace();
			 //没有找到这个用户，激活失败
			request.setAttribute("msg", "激活失败，请重新激活或者重新注册！");
			return "/jsp/msg.jsp";
		}
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1、封装对象
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			
			//1.1手动封装uid
			user.setUid(UUIDUtils.getId());
			//1.2手动封装激活状态 state
			user.setState(Constant.USER_IS_NOT_ACTIVE);
			//1.3手动封装激活码 code
			user.setCode(UUIDUtils.getCode());
			//2、调用service完成注册
			UserService us = new UserServiceImpl();
			us.regist(user);
			//3、页面转发  提示信息
			request.setAttribute("msg", "恭喜你，注册成功，请登录邮箱完成激活！");
		} catch (Exception e) {
			e.printStackTrace();
			//转发到msg.jsp
			request.setAttribute("msg", "用户注册失败！");
			return "/jsp/msg.jsp";
		} 
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 跳转到注册页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}
}
