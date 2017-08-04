package com.liu.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liu.domain.Cart;
import com.liu.domain.CartItem;
import com.liu.domain.Product;
import com.liu.service.ProductService;
import com.liu.utils.BeanFactory;
import com.liu.web.servlet.base.BaseServlet;

/**
 * ���ﳵģ��
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��չ��ﳵ
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1. ��ȡ���ﳵ ִ�����
		getCart(request).clearCart();
		//2.�ض���
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	
	/**
	 * �ӹ��ﳵ���Ƴ�
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��ȡ��Ʒ��pid
		String pid = request.getParameter("pid");
		//2.��ȡ���ﳵ ִ���Ƴ�
		getCart(request).removeFromCart(pid);
		//3.�ض���
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	/**
	 * ���빺�ﳵ
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add2cart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.��ȡpid count
			String pid = request.getParameter("pid");
			int count = Integer.parseInt(request.getParameter("count"));
			//2.��װcartitem
			//����service��ȡproduct
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.getById(pid);
			
			//����cartitem
			CartItem cartItem = new CartItem(product, count);
			//3.��Cartitem���빺�ﳵ 
			//��ȡ���ﳵ
			Cart cart = getCart(request);
			cart.add2cart(cartItem);
			//4.�ض���
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		}  catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","���빺�ﳵʧ��");
			return "/jsp/msg.jsp";
		}
		return null;
	}

	/**
	 * ��ȡ���ﳵ
	 * @param request
	 * @return
	 */
	private Cart getCart(HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
			//��cart���뵽session��
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}

}
