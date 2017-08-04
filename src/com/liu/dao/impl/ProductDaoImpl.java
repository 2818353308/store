package com.liu.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.liu.constant.Constant;
import com.liu.dao.ProductDao;
import com.liu.domain.PageBean;
import com.liu.domain.Product;
import com.liu.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {

	/**
	 * ��ѯ����
	 */
	public List<Product> findHot() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = ? and pflag = ? order by pdate desc limit 9";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), Constant.PRODUCT_IS_HOT,Constant.PRODUCT_IS_UP);
	}

	/**
	 * ��ѯ����
	 */
	public List<Product> findNew() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pflag = ? order by pdate desc limit 9";
		return qr.query(sql, new BeanListHandler<Product>(Product.class),Constant.PRODUCT_IS_UP);
	}

	/**
	 * ��ѯ������Ʒ
	 */
	public Product getById(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ? limit 1";
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
		
	}

	/**
	 * ��ѯ��ǰҳ����
	 */
	public List<Product> findByPage(PageBean<Product> pb, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? and pflag = ? order by pdate desc limit ?,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid,Constant.PRODUCT_IS_UP,pb.getStartIndex(),pb.getPageSize());
	}

	/**
	 * ��ȡ�ܼ�¼��
	 */
	public int getTotalRecord(String cid) throws Exception {
		return ((Long)new QueryRunner(DataSourceUtils.getDataSource()).query("select count(*) from product where cid = ? and pflag = ?", new ScalarHandler(), cid,Constant.PRODUCT_IS_UP)).intValue();
	}
	
	/**
	 * ��̨չʾ���ϼ���Ʒ 
	 */
	public List<Product> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pflag = ? order by pdate desc";
		return qr.query(sql, new BeanListHandler<Product>(Product.class),Constant.PRODUCT_IS_UP);
	}
	
	/**
	 * �����Ʒ
	 */
	public void save(Product p) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/*
		 * `pid` varchar(32) NOT NULL,
			  `pname` varchar(50) DEFAULT NULL,
			  `market_price` double DEFAULT NULL,
			  
			  `shop_price` double DEFAULT NULL,
			  `pimage` varchar(200) DEFAULT NULL,
			  `pdate` date DEFAULT NULL,
			  
			  `is_hot` int(11) DEFAULT NULL,
			  `pdesc` varchar(255) DEFAULT NULL,
			  `pflag` int(11) DEFAULT NULL,
			  
			  `cid` varchar(32) DEFAULT NULL,
		 */
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql, p.getPid(),p.getPname(),p.getMarket_price(),
				p.getShop_price(),p.getPimage(),p.getPdate(),
				p.getIs_hot(),p.getPdesc(),p.getPflag(),
				p.getCategory().getCid());

		
	}
	
	public void delete(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid=?";
		qr.update(sql, pid);
	}
}
