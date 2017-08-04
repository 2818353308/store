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
 * ��̨��Ʒ����
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ɾ����Ʒ
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//��ȡpid
			String pid = request.getParameter("pid");
			//����serviceɾ�� ��Ʒ
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.delete(pid);
			//�ض���
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ɾ����Ʒ�����쳣");
		}
		return null;
	}
	/**
	 * ��ת�������Ʒҳ��
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//����categoryservice��ѯ���еķ���
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			request.setAttribute("list", cs.findList());
		} catch (Exception e) {
		}
		return "/admin/product/add.jsp";
	}
	
    /**
     * չʾ���ϼ���Ʒ�б�
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	try {
			// 1.����service��ѯ���ϼ���Ʒ
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findAll();
			//2.������ֵ����request ����ת��
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("չʾ���ϼ���Ʒ�б������");
		}
    	
    	return "/admin/product/list.jsp";
    }

}
