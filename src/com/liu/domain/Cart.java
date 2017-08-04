package com.liu.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Cart {
	
	private Map<String, CartItem> itemMap = new HashMap<String, CartItem>();
	private Double total = 0.0;
	/**
	 * ��ȡ���еĹ�����
	 */
	public Collection<CartItem> getCartItems(){
		return itemMap.values();
	}
	
	public Map<String, CartItem> getItemMap() {
		return itemMap;
	}
	public void setItemMap(Map<String, CartItem> itemMap) {
		this.itemMap = itemMap;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	/**
	 * ���빺�ﳵ
	 * @param item
	 */
	public void add2cart(CartItem item){
		//��ȡ��Ʒ��id
		String pid = item.getProduct().getPid();
		//�жϹ��ﳵ���Ƿ���
		if(itemMap.containsKey(pid)){
			//�� �޸����� = ԭ��������+�¼ӵ�����
			//ԭ�еĹ�����
			CartItem oItem = itemMap.get(pid);
						//ԭ�������� 			+�¼ӵ�����
			oItem.setCount(oItem.getCount()+item.getCount());
			
		}else{
			itemMap.put(pid, item);
		}
		//�޸��ܽ��
		total+=item.getSubtotal();
	}
	
	/**
	 * �ӹ��ﳵ�Ƴ�һ��������
	 * @param item
	 */
	public void removeFromCart(String pid){
		//1.�ӹ��ﳵ��map���Ƴ�������
		CartItem item =itemMap.remove(pid);
		//2.�޸��ܽ��
		total -=item.getSubtotal();
	}
	
	/**
	 * ��չ��ﳵ
	 * @param item
	 */
	public void clearCart(){
		//1.���map
		itemMap.clear();
		//2.�޸��ܽ�� = 0
		total = 0.0;
	}
	
	
}
