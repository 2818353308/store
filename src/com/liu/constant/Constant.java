package com.liu.constant;

public interface Constant {
	/**
	 * �û�δ����
	 */
	int USER_IS_NOT_ACTIVE = 0;
	/**
	 * �û��Ѽ���
	 */
	int USER_IS_ACTIVE = 1;
	
	/**
	 * ��ס�û���
	 */
	String SAVE_NAME = "ok";
	
	/**
	 * redis�д洢�����б��key
	 */
	String STORE_CATEGORY_LIST="STORE_CATEGORY_LIST";
	
	/**
	 * redis�ķ�������ַ192.168.17.136"
	 */
	String REDIS_HOST = "192.168.17.136";
	
	/**
	 * redis�ķ������˿ں�
	 */
	int REDIS_POST = 6379;
	/**
	 * ������Ʒ
	 */
	int PRODUCT_IS_HOT = 1;
	
	/**
	 * ��Ʒ���ϼ�
	 */
	int PRODUCT_IS_UP = 0;
	
	/**
	 * ��Ʒ���¼�
	 */
	int PRODUCT_IS_DOWN = 1;
	/**
	 * ����״̬ δ����
	 */
	int ORDER_WEIFUKUAN=0;
	
	/**
	 * ����״̬ �Ѹ���
	 */
	int ORDER_YIFUKUAN=1;
	
	/**
	 * ����״̬ �ѷ���
	 */
	int ORDER_YIFAHUO=2;
	
	/**
	 * ����״̬ �����
	 */
	int ORDER_YIWANCHENG=3;
}
