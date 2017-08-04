package com.liu.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.liu.constant.Constant;
import com.liu.domain.Order;
import com.liu.domain.OrderItem;
import com.liu.service.OrderService;
import com.liu.utils.BeanFactory;
import com.liu.utils.JsonUtil;
import com.liu.web.servlet.base.BaseServlet;

/**
 * 后台订单模块
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 修改订单状态
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("aa");
		try {
			// 1.获取oid
			String oid = request.getParameter("oid");
			//2.调用service 获取订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			//3.设置订单状态，更新
			order.setState(Constant.ORDER_YIFAHUO);
			os.update(order);
			//4.重定向
			response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 展示订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String showDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//设置编码
			response.setContentType("text/html;charset=utf-8");
			
			// 1.获取oid
			String oid = request.getParameter("oid");
			//2.调用service
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			//3.获取订单列表转发json 写回浏览器
			if(order!=null){
				List<OrderItem> list = order.getItems();
				if(list!=null&&list.size()>0){
					//response.getWriter().println(JsonUtil.list2json(list));
					JsonConfig config =JsonUtil.configJson(new String[]{"order","pdate","pdesc","itemid"});
					response.getWriter().println(JSONArray.fromObject(list, config));
				}
			}
			
		} catch (Exception e) {
 			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 后台按状态查询订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 获取state
			String state = request.getParameter("state");
			//调用service获取不同的列表
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			List<Order> list = os.findAllByState(state);
			//将list放入到request域中，请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/order/list.jsp";
	}

	
	
	
}
