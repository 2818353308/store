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
 * 后台分类管理模块
 */
public class AdminCategoryService extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 添加分类
	 */
	public String save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 封装category对象
			Category c = new Category();
			c.setCid(UUIDUtils.getId());
			c.setCname(request.getParameter("cname"));
			//2.调用service 	完成添加操作
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.save(c);
			//3.重定向
			response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	
	/**
	 * 跳转到添加页面
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/admin/category/add.jsp";
	}
	
	/**
	 * 展示所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.调用service 获取所有返回值
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list =  cs.findList();
			//2.将返回值放入到request请求中 转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/category/list.jsp";
	}
	
	
	
	
}
