package com.liu.web.servlet;

import java.io.IOException;
import java.util.List;

import com.liu.domain.Product;
import com.liu.service.ProductService;
import com.liu.service.impl.ProductServiceImpl;
import com.liu.web.servlet.base.BaseServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��ҳģ��
 */
public class IndexServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 1L;

    
	@Override
	public String index(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.����productservice��ѯ������Ʒ��������Ʒ
			ProductService ps = new ProductServiceImpl();
			List<Product> hotList = ps.findHot();
			List<Product> newList = ps.findNew();
			//2.������list������request����
			request.setAttribute("hlist", hotList);
			request.setAttribute("nlist", newList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/index.jsp";
	}

}
