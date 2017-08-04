package com.liu.service.impl;

import java.util.List;

import redis.clients.jedis.Jedis;

import com.liu.constant.Constant;
import com.liu.dao.CategoryDao;
import com.liu.domain.Category;
import com.liu.service.CategoryService;
import com.liu.utils.BeanFactory;
import com.liu.utils.JedisUtils;
import com.liu.utils.JsonUtil;

public class CategoryServiceImpl implements CategoryService {
	
	/**
	 * ��̨չʾ���з���
	 */
	public List<Category> findList() throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.findAll();
	}

	/**
	 * ��ѯ���з���
	 */
	public String findAll() throws Exception {
		/*//1.����dao ��ѯ����
		CategoryDao cd = new CategoryDaoImpl();*/
		List<Category> list = findList();
		//2.��listת����json�ַ���
		if(list!=null&&list.size()>0){
			return JsonUtil.list2json(list);
		}
		return null;
	}

	/**
	 * ��redis�л�ȡ���з���
	 */
	public String findAllFromRedis() throws Exception {
		//1.��ȡjedis
		/*Jedis jedis = JedisUtils.getJedis();
		//2.��redis�л�ȡ����
		String value = jedis.get(Constant.STORE_CATEGORY_LIST);
		//3.�ж������Ƿ�Ϊ��
		if(value == null){
			//3.1��Ϊ�գ�����findAll������������ѯ�Ľ������redis
			value = findAll();
			jedis.set(Constant.STORE_CATEGORY_LIST, value);
			System.out.println("��mysql�л�ȡ");
			return value;
		}
		//3.2����Ϊ��,return
		System.out.println("��redis�л�ȡ");
		return value;*/
		Jedis j =null;
		String value=null;
		try {
			//1.��redis��ȡ������Ϣ
			try {
				//1.1��ȡ����
				j = JedisUtils.getJedis();
				
				//1.2 ��ȡ���� �ж������Ƿ�Ϊ��
				value = j.get(Constant.STORE_CATEGORY_LIST);
				
				//1.3 ����Ϊ��,ֱ�ӷ�������
				if(value!=null){
					System.out.println("������������");
					return value;
				}
			} catch (Exception e) {
			}
			
			//2 redis�� ��������,���mysql���ݿ��л�ȡ  �����ǽ����ݲ�����redis��
			value = findAll();
					
			
			//3.��value����redis��
			try {
				j.set(Constant.STORE_CATEGORY_LIST, value);
				System.out.println("�Ѿ������ݷ��뻺����");
			} catch (Exception e) {
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//�ͷ�jedis
			JedisUtils.closeJedis(j);
		}
		
		return value;
	}

	/**
	 * ��ӷ���
	 */
	public void save(Category c) throws Exception {
		//1.����dao ������
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.save(c);
		
		//2.����redis
		/*Jedis j = null;
		try {
			j=JedisUtils.getJedis();
			//���redis������
			j.del(Constant.STORE_CATEGORY_LIST);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JedisUtils.closeJedis(j);
		}*/
	}

}
