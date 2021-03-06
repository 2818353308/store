package com.liu.dao;

import java.util.List;

import com.liu.domain.Order;
import com.liu.domain.OrderItem;
import com.liu.domain.PageBean;

public interface OrderDao {

	void save(Order order) throws Exception;

	void saveItem(OrderItem oi) throws Exception;

	int getTotalRecore(String uid)throws Exception;

	List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid)throws Exception;

	Order getById(String oid)throws Exception;

	void update(Order order)throws Exception;

	List<Order> findAllByState(String state)throws Exception;

}
