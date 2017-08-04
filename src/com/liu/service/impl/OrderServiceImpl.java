package com.liu.service.impl;

import java.util.List;

import com.liu.dao.OrderDao;
import com.liu.domain.Order;
import com.liu.domain.OrderItem;
import com.liu.domain.PageBean;
import com.liu.service.OrderService;
import com.liu.utils.BeanFactory;
import com.liu.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService{

	/**
	 * ���涩��
	 */
	public void save(Order order) throws Exception {
		try {
			// ��ȡdao
			OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
			//1.��������
			DataSourceUtils.startTransaction();
			//2.��orders���в���һ��
			od.save(order);
			//3.��orderitem�в���n��
			for(OrderItem oi:order.getItems()){
				od.saveItem(oi);
			}
			//4.�������
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
	/**
	 * �ҵĶ���
	 */
	public PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize,
			String uid) throws Exception {
		OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		//1.����pagebean
		PageBean<Order> pb = new PageBean<Order>(pageNumber, pageSize);
		//2.��ѯ������������������
		int totalRecode = od.getTotalRecore(uid);
		pb.setTotalRecord(totalRecode);
		//3.��ѯ��ǰҳ���� ���õ�ǰҳ����
		List<Order> data = od.findMyOrdersByPage(pb,uid);
		pb.setData(data);
		return pb;
	}
	/**
	 * ��������
	 */
	public Order getById(String oid) throws Exception {
		OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		return od.getById(oid);
	}
	/**
	 * �޸Ķ���
	 */
	public void update(Order order) throws Exception {
		OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		od.update(order);
	}
	
	/**
	 * ��̨��ѯ�����б�
	 */
	public List<Order> findAllByState(String state) throws Exception {
		OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		return od.findAllByState(state);
	}

}
