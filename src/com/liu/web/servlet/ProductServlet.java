package com.liu.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liu.domain.PageBean;
import com.liu.domain.Product;
import com.liu.service.ProductService;
import com.liu.service.impl.ProductServiceImpl;
import com.liu.web.servlet.base.BaseServlet;

/**
 * ǰ̨��Ʒģ��
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ������Ʒ��ҳչʾ
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.��ȡpagenumber cid ����pagesize
		//String parameter = request.getParameter("pageNumber");
		try {
			int pageNumber = 1;
			try {
				pageNumber  = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (NumberFormatException e) {
			}
			int pageSize = 12;
			String cid = request.getParameter("cid");
			
			//2.����service ��ҳ��ѯ��Ʒ������3��������ֵ��pagebean
			ProductService ps = new ProductServiceImpl();
			PageBean<Product> bean = ps.findByPage(pageNumber,pageSize,cid);
			
			//3.��pagebean����request�У�����ת��product_list.jsp
			request.setAttribute("pb", bean);
		} catch (Exception e) {
			request.setAttribute("msg", "��ҳ��ѯʧ��");
			return "/jsp/msg.jsp";
		}
		return "/jsp/product_list.jsp";
	}
	
	
	/**
	 * ��Ʒ����
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.��ȡpid
			String pid = request.getParameter("pid");
			
			//2.����service��ȡ������Ʒ������pid ����ֵ ��product
			ProductService ps = new ProductServiceImpl();
			Product pro = ps.getById(pid);
			//3.��product����request���У�����ת��/jsp/product_info.jsp
			request.setAttribute("bean", pro);
		} catch (Exception e) {
			request.setAttribute("msg", "��ѯ������Ʒʧ��");
			return "/jsp/msg.jsp";
		}
		return "/jsp/product_info.jsp";
	}

}
