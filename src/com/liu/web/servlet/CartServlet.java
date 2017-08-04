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
 * 购物车模块
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 清空购物车
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1. 获取购物车 执行清空
		getCart(request).clearCart();
		//2.重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	
	/**
	 * 从购物车中移除
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取商品的pid
		String pid = request.getParameter("pid");
		//2.获取购物车 执行移除
		getCart(request).removeFromCart(pid);
		//3.重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	/**
	 * 加入购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add2cart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1.获取pid count
			String pid = request.getParameter("pid");
			int count = Integer.parseInt(request.getParameter("count"));
			//2.封装cartitem
			//调用service获取product
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.getById(pid);
			
			//创建cartitem
			CartItem cartItem = new CartItem(product, count);
			//3.将Cartitem加入购物车 
			//获取购物车
			Cart cart = getCart(request);
			cart.add2cart(cartItem);
			//4.重定向
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		}  catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","加入购物车失败");
			return "/jsp/msg.jsp";
		}
		return null;
	}

	/**
	 * 获取购物车
	 * @param request
	 * @return
	 */
	private Cart getCart(HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
			//将cart放入到session中
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}

}
