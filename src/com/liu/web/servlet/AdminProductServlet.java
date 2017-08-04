package com.liu.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liu.domain.Product;
import com.liu.service.CategoryService;
import com.liu.service.ProductService;
import com.liu.utils.BeanFactory;
import com.liu.web.servlet.base.BaseServlet;

/**
 * 后台商品管理
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 删除商品
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//获取pid
			String pid = request.getParameter("pid");
			//调用service删除 商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.delete(pid);
			//重定向
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除商品出现异常");
		}
		return null;
	}
	/**
	 * 跳转到添加商品页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//调用categoryservice查询所有的分类
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			request.setAttribute("list", cs.findList());
		} catch (Exception e) {
		}
		return "/admin/product/add.jsp";
	}
	
    /**
     * 展示以上架商品列表
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	try {
			// 1.调用service查询已上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findAll();
			//2.将返回值放入request 请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("展示以上架商品列表出错了");
		}
    	
    	return "/admin/product/list.jsp";
    }

}
