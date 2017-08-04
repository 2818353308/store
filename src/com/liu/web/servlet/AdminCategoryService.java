package com.liu.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liu.domain.Category;
import com.liu.service.CategoryService;
import com.liu.utils.BeanFactory;
import com.liu.utils.UUIDUtils;
import com.liu.web.servlet.base.BaseServlet;

/**
 * ��̨�������ģ��
 */
public class AdminCategoryService extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ӷ���
	 */
	public String save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// ��װcategory����
			Category c = new Category();
			c.setCid(UUIDUtils.getId());
			c.setCname(request.getParameter("cname"));
			//2.����service 	�����Ӳ���
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.save(c);
			//3.�ض���
			response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	
	/**
	 * ��ת�����ҳ��
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/admin/category/add.jsp";
	}
	
	/**
	 * չʾ���з���
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.����service ��ȡ���з���ֵ
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list =  cs.findList();
			//2.������ֵ���뵽request������ ת��
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/category/list.jsp";
	}
	
	
	
	
}
