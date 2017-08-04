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
	 * 保存订单
	 */
	public void save(Order order) throws Exception {
		try {
			// 获取dao
			OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
			//1.开启事务
			DataSourceUtils.startTransaction();
			//2.向orders表中插入一条
			od.save(order);
			//3.向orderitem中插入n条
			for(OrderItem oi:order.getItems()){
				od.saveItem(oi);
			}
			//4.事务控制
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
	/**
	 * 我的订单
	 */
	public PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize,
			String uid) throws Exception {
		OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		//1.创建pagebean
		PageBean<Order> pb = new PageBean<Order>(pageNumber, pageSize);
		//2.查询总条数，设置总条数
		int totalRecode = od.getTotalRecore(uid);
		pb.setTotalRecord(totalRecode);
		//3.查询当前页数据 设置当前页数据
		List<Order> data = od.findMyOrdersByPage(pb,uid);
		pb.setData(data);
		return pb;
	}
	/**
	 * 订单详情
	 */
	public Order getById(String oid) throws Exception {
		OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		return od.getById(oid);
	}
	/**
	 * 修改订单
	 */
	public void update(Order order) throws Exception {
		OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		od.update(order);
	}
	
	/**
	 * 后台查询订单列表
	 */
	public List<Order> findAllByState(String state) throws Exception {
		OrderDao od = (OrderDao)BeanFactory.getBean("OrderDao");
		return od.findAllByState(state);
	}

}
