package com.liu.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liu.constant.Constant;
import com.liu.domain.Cart;
import com.liu.domain.CartItem;
import com.liu.domain.Order;
import com.liu.domain.OrderItem;
import com.liu.domain.PageBean;
import com.liu.domain.User;
import com.liu.service.OrderService;
import com.liu.utils.BeanFactory;
import com.liu.utils.PaymentUtil;
import com.liu.utils.UUIDUtils;
import com.liu.web.servlet.base.BaseServlet;

/**
 * ����ģ��
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��ȡ��������
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.��ȡoid
			String oid = request.getParameter("oid");
			
			//2.����service ��ѯ�������� 
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			
			//3.����ת��
			request.setAttribute("bean",order);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "��ѯ��������ʧ��");
			return "/jsp/msg.jsp";
		}
		return "/jsp/order_info.jsp";
	}
	
	
	/**
	 * �ҵĶ���
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findMyOrdersByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.��ȡpageNumber ����pagesize ��ȡuserid
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize=3;
			
			User user=(User)request.getSession().getAttribute("user");
			if(user == null){
				//δ��¼ ��ʾ
				request.setAttribute("msg", "���ȵ�¼");
				return "/jsp/msg.jsp";
			}
			
			//2.����service��ȡ��ǰҳ��������  pagebean
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			PageBean<Order> bean = os.findMyOrdersByPage(pageNumber,pageSize,user.getUid());
			
			//3.��pagebean����request����,����ת�� order_list.jsp
			request.setAttribute("pb", bean);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "��ȡ�ҵĶ���ʧ��");
			return "/jsp/msg.jsp";
		}
		return "/jsp/order_list.jsp";
	}
	/**
	 * ���涩��
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//-1.��session�л�ȡuser
			User user=(User) request.getSession().getAttribute("user");
			if(user == null){
				//δ��¼ ��ʾ
				request.setAttribute("msg", "���ȵ�¼!");
				return "/jsp/msg.jsp";
			}
			
			//0.��ȡ���ﳵ
			Cart cart=(Cart) request.getSession().getAttribute("cart");
			
			//1.��װ��������
			//1.1��������
			Order order = new Order();
			
			//1.2����oid 
			order.setOid(UUIDUtils.getId());
			
			//1.3����ordertime
			order.setOrdertime(new Date());
			
			//1.4����total ���ﳵ��
			order.setTotal(cart.getTotal());
			
			//1.5����state
			order.setState(Constant.ORDER_WEIFUKUAN);
			
			//1.6����user
			order.setUser(user);
			
			//1.7����items(�������б�) �����������б�
			for (CartItem	ci : cart.getCartItems()) {
				//1.7.1��װ��orderitem
				//a.����orderitem
				OrderItem oi = new OrderItem();
				
				//b.����itemid uuid
				oi.setItemid(UUIDUtils.getId());
				
				//c.����count ��ci�л�ȡ
				oi.setCount(ci.getCount());
				
				//d.����subtotal ��ci�л�ȡ
				oi.setSubtotal(ci.getSubtotal());
				
				//e.����product ��ci�л�ȡ
				oi.setProduct(ci.getProduct());
				
				//f.����order 
				oi.setOrder(order);
				
				//1.7.2 ��orderitem����order ��items��
				order.getItems().add(oi);
			}
			
			
			//2.����orderservice��ɱ������
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			os.save(order);
			
			cart.clearCart();
			
			//3.����ת���� order_info.jsp
			request.setAttribute("bean", order);
		} catch (Exception e) {
			
		}
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * ����֧��
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ�ջ���Ϣ ��ȡoid ��ȡ����
		//2.����service��ȡ���� �޸��ջ�����Ϣ  ���¶���
		
		//3.ƴ�Ӹ���������url
		
		//4.�ض���
		try {
			//���ܲ���
			String address=request.getParameter("address");
			String name=request.getParameter("name");
			String telephone=request.getParameter("telephone");
			String oid=request.getParameter("oid");
			
			
			//ͨ��id��ȡorder
			OrderService s=(OrderService) BeanFactory.getBean("OrderService");
			Order order = s.getById(oid);
			
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			
			//����order
			s.update(order);
			

			// ��֯����֧����˾��Ҫ��Щ����
			String pd_FrpId = request.getParameter("pd_FrpId");
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// ֧���ɹ��ص���ַ ---- ������֧����˾����ʡ��û�����
			// ������֧�����Է�����ַ
			String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// ����hmac ��Ҫ��Կ
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
					p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
					pd_FrpId, pr_NeedResponse, keyValue);

			
			//���͸�������
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			response.sendRedirect(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "֧��ʧ��");
			return "/jsp/msg.jsp";
		}
		
		return null;
	}
	
	
	/**
	 * ֧���ɹ�֮��Ļص�
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ���������͹���������
		
		//2.��ȡ���� �޸Ķ���״̬
		
		//3.���¶���
		
		try {
			String p1_MerId = request.getParameter("p1_MerId");
			String r0_Cmd = request.getParameter("r0_Cmd");
			String r1_Code = request.getParameter("r1_Code");
			String r2_TrxId = request.getParameter("r2_TrxId");
			String r3_Amt = request.getParameter("r3_Amt");
			String r4_Cur = request.getParameter("r4_Cur");
			String r5_Pid = request.getParameter("r5_Pid");
			String r6_Order = request.getParameter("r6_Order");
			String r7_Uid = request.getParameter("r7_Uid");
			String r8_MP = request.getParameter("r8_MP");
			String r9_BType = request.getParameter("r9_BType");
			String rb_BankId = request.getParameter("rb_BankId");
			String ro_BankOrderId = request.getParameter("ro_BankOrderId");
			String rp_PayDate = request.getParameter("rp_PayDate");
			String rq_CardNo = request.getParameter("rq_CardNo");
			String ru_Trxtime = request.getParameter("ru_Trxtime");
			// ���У�� --- �ж��ǲ���֧����˾֪ͨ��
			String hmac = request.getParameter("hmac");
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
					"keyValue");

			// �Լ����������ݽ��м��� --- �Ƚ�֧����˾������hamc
			boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
					r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
					r8_MP, r9_BType, keyValue);
			if (isValid) {
				// ��Ӧ������Ч
				if (r9_BType.equals("1")) {
					// ������ض���
					System.out.println("111");
					request.setAttribute("msg", "���Ķ�����Ϊ:"+r6_Order+",���Ϊ:"+r3_Amt+"�Ѿ�֧���ɹ�,�ȴ�����~~");
					
				} else if (r9_BType.equals("2")) {
					// ��������Ե� --- ֧����˾֪ͨ��
					System.out.println("����ɹ���222");
					// �޸Ķ���״̬ Ϊ�Ѹ���
					// �ظ�֧����˾
					response.getWriter().print("success");
				}
				
				//�޸Ķ���״̬
				OrderService s=(OrderService) BeanFactory.getBean("OrderService");
				Order order = s.getById(r6_Order);
				order.setState(Constant.ORDER_YIFUKUAN);
				
				s.update(order);
				
			} else {
				// ������Ч
				System.out.println("���ݱ��۸ģ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "֧��ʧ��");
		}
		
		
		return "/jsp/msg.jsp";
	}
}
