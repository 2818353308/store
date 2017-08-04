package com.liu.service.impl;

import java.util.List;

import com.liu.dao.ProductDao;
import com.liu.domain.PageBean;
import com.liu.domain.Product;
import com.liu.service.ProductService;
import com.liu.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	/**
	 * ��ѯ������Ʒ
	 */
	public List<Product> findHot() throws Exception {
//		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findHot();
	}
	/**
	 * ��ѯ������Ʒ
	 */
	public List<Product> findNew() throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findNew();
	}
	/**
	 * ������Ʒ����
	 */
	public Product getById(String pid) throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		
		return pd.getById(pid);
	}
	
	/**
	 * ��ҳչʾ������Ʒ
	 */
	public PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		//1.����pagebean
		PageBean<Product> pb = new PageBean<Product>(pageNumber, pageSize);
		
		//2.���õ�ǰҳ����
		List<Product> data = pDao.findByPage(pb,cid);
		pb.setData(data);
		
		//3.�����ܼ�¼��
		int totalRecord = pDao.getTotalRecord(cid);
		pb.setTotalRecord(totalRecord);
		
		return pb;
	}
	
	/**
	 * ��̨չʾ���ϼ���Ʒ
	 */
	public List<Product> findAll() throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		return pDao.findAll();
	}
	
	/**
	 * ������Ʒ
	 */
	public void save(Product p) throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");		
		pDao.save(p);
	}
	
	/**
	 * ɾ����Ʒ
	 */
	public void delete(String pid) throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");		
		pDao.delete(pid);
	}

}
