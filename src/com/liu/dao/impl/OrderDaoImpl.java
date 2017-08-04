package com.liu.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.liu.dao.OrderDao;
import com.liu.domain.Order;
import com.liu.domain.OrderItem;
import com.liu.domain.PageBean;
import com.liu.domain.Product;
import com.liu.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {

	/**
	 * ���涩��
	 */
	public void save(Order o) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, o.getOid(),o.getOrdertime(),o.getTotal(),
				o.getState(),o.getAddress(),o.getName(),
				o.getTelephone(),o.getUser().getUid());
	}
	/**
	 * ���涩����
	 */
	public void saveItem(OrderItem oi) throws Exception {
		QueryRunner qr = new QueryRunner();
		/*
		 * `itemid` varchar(32) NOT NULL,
				  `count` int(11) DEFAULT NULL,
				  `subtotal` double DEFAULT NULL,
				  
				  `pid` varchar(32) DEFAULT NULL,
				  `oid` varchar(32) DEFAULT NULL,
		 */
		String sql = "insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, oi.getItemid(),oi.getCount(),oi.getSubtotal(),
				oi.getProduct().getPid(),oi.getOrder().getOid());
		
	}
	/**
	 * ��ȡ�ҵĶ�����������
	 */
	public int getTotalRecore(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(), uid)).intValue();
	}

	/**
	 * ��ȡ�ҵĶ��� ��ǰҳ����
	 */
	public List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//��ѯ���ж���(������Ϣ)
		String sql="select * from orders where uid = ? order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class), uid,pb.getStartIndex(),pb.getPageSize());
		
		//������������ ��ȡÿһ������,��ѯÿ������������
		for (Order order : list) {
			sql="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
			List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler(), order.getOid());
			//����maplist ��ȡÿһ������������,��װ��orderitem,������뵱ǰ�����Ķ������б���
			for (Map<String, Object> map : maplist) {
				//1.��װ��orderitem
				//a.����orderitem
				OrderItem oi = new OrderItem();
				
				//b.��װorderitem
				BeanUtils.populate(oi, map);
				
				//c.�ֶ���װproduct
				Product p = new Product();
				
				BeanUtils.populate(p, map);
				
				oi.setProduct(p);
				
				//2.��orderitem����order�Ķ������б�
				order.getItems().add(oi);
			}
		}
		return list;
	}
	

	
	/**
	 * ��������
	 */
	public Order getById(String oid) throws Exception {
		//1.��ѯ����������Ϣ
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql ="select * from orders where oid = ?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		
		//2.��ѯ������
		sql ="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
		//���еĶ���������
		List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler(), oid);
		
		//���� ��ȡÿһ������������ ��װ��orderitem ���뵽��ǰ������items��
		for (Map<String, Object> map : maplist) {
			//����ordreitem
			OrderItem oi = new OrderItem();
			//��װ
			BeanUtils.populate(oi, map);
			
			//�ֶ���װproduct
			Product p = new Product();
			BeanUtils.populate(p, map);
			
			oi.setProduct(p);
			//��orderitem���뵽������items��
			order.getItems().add(oi);
		}
		return order;
	}

	public void update(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/**
		 * `oid` varchar(32) NOT NULL,
			  `ordertime` datetime DEFAULT NULL,
			  `total` double DEFAULT NULL,
			  
			  `state` int(11) DEFAULT NULL,
			  `address` varchar(30) DEFAULT NULL,
			  `name` varchar(20) DEFAULT NULL,
			  
			  `telephone` varchar(20) DEFAULT NULL,
			  `uid` varchar(32) DEFAULT NULL,
		 */
		String sql="update orders set state = ?,address = ?,name =?,telephone = ? where oid = ?";
		qr.update(sql,order.getState(),order.getAddress(),order.getName(),
				order.getTelephone(),order.getOid());
		
	}
	
	/**
	 * ��̨��ѯ�����б�
	 */
	public List<Order> findAllByState(String state) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders";
		//�ж�state�Ƿ�Ϊ��
		if(state==null||state.trim().length()==0){
			sql+=" order by ordertime desc";
			return qr.query(sql, new BeanListHandler<Order>(Order.class));
		}
		sql+=" where state = ? order by ordertime desc";
		
		return qr.query(sql, new BeanListHandler<Order>(Order.class),state);
	}

}
