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
 * �û�ģ��
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
	 * �û���¼
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.��ȡ�û���������
			String username = request.getParameter("username"); 
			String password = request.getParameter("password"); 
			//2.����service��ɵ�¼  ����ֵ��user
			UserService us = new UserServiceImpl();
			User user = us.login(username,password);
			//3.�ж�user ���ݽ��������ʾ
			if(user == null){
				//�û��������벻ƥ��
				request.setAttribute("msg", "�û��������벻ƥ��");
				return "/jsp/login.jsp";
			}
			//���û�����Ϊ�գ������ж��Ƿ񼤻�
			if(Constant.USER_IS_ACTIVE!=user.getState()){
				//δ����
				request.setAttribute("msg","���ȼ����ٵ�¼");
				return "/jsp/msg.jsp";
			}
			//��¼ �����û���¼״̬
			request.getSession().setAttribute("user", user);
			//*******************��ס�û���**************************
			//�ж��Ƿ�ѡ�˼�ס�û���
			if(Constant.SAVE_NAME.equals(request.getParameter("savenaem"))){
				Cookie c = new Cookie( "saveName", URLEncoder.encode(username, "utf-8"));
				c.setMaxAge(Integer.MAX_VALUE);
				c.setPath(request.getContextPath()+"/");
				response.addCookie(c);
			}
			//��ת��index.jsp
			response.sendRedirect(request.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","��¼ʧ��");
			return "/jsp/msg.jsp";
		}
		return null;
	}
	
	/**
	 * ��ת����¼ҳ
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
	 * �û�����
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.����code
			String code = request.getParameter("code");
			//2.����service��ɼ��� ����ֵ��user
			UserService us = new UserServiceImpl();
			User user = us.active(code);
			
			//3.�ж�user���ɲ�ͬ����ʾ��Ϣ
			if(user == null){
				 //û���ҵ�����û�������ʧ��
				request.setAttribute("msg", "����ʧ�ܣ������¼����������ע�ᣡ");
				return "/jsp/msg.jsp";
			}
			//����ɹ�
			request.setAttribute("msg", "����ɹ������Ե�¼��");
		} catch (Exception e) {
			e.printStackTrace();
			 //û���ҵ�����û�������ʧ��
			request.setAttribute("msg", "����ʧ�ܣ������¼����������ע�ᣡ");
			return "/jsp/msg.jsp";
		}
		return "/jsp/msg.jsp";
	}
	
	/**
	 * �û�ע��
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1����װ����
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			
			//1.1�ֶ���װuid
			user.setUid(UUIDUtils.getId());
			//1.2�ֶ���װ����״̬ state
			user.setState(Constant.USER_IS_NOT_ACTIVE);
			//1.3�ֶ���װ������ code
			user.setCode(UUIDUtils.getCode());
			//2������service���ע��
			UserService us = new UserServiceImpl();
			us.regist(user);
			//3��ҳ��ת��  ��ʾ��Ϣ
			request.setAttribute("msg", "��ϲ�㣬ע��ɹ������¼������ɼ��");
		} catch (Exception e) {
			e.printStackTrace();
			//ת����msg.jsp
			request.setAttribute("msg", "�û�ע��ʧ�ܣ�");
			return "/jsp/msg.jsp";
		} 
		return "/jsp/msg.jsp";
	}
	
	/**
	 * ��ת��ע��ҳ��
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
