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
 * ��̨����ģ��
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * �޸Ķ���״̬
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
			// 1.��ȡoid
			String oid = request.getParameter("oid");
			//2.����service ��ȡ����
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			//3.���ö���״̬������
			order.setState(Constant.ORDER_YIFAHUO);
			os.update(order);
			//4.�ض���
			response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * չʾ��������
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String showDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//���ñ���
			response.setContentType("text/html;charset=utf-8");
			
			// 1.��ȡoid
			String oid = request.getParameter("oid");
			//2.����service
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			//3.��ȡ�����б�ת��json д�������
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
	 * ��̨��״̬��ѯ�����б�
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// ��ȡstate
			String state = request.getParameter("state");
			//����service��ȡ��ͬ���б�
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			List<Order> list = os.findAllByState(state);
			//��list���뵽request���У�����ת��
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/order/list.jsp";
	}

	
	
	
}
