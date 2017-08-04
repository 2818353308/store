package com.liu.utils;
import java.util.Random;
import java.util.UUID;

public class UploadUtils {
	/**
	 * ��ȡ�ļ���ʵ����
	 * ����������Ĳ�ͬ��ȡ�����ƿ���Ϊ:c:/upload/1.jpg����1.jpg 
	 * ���ջ�ȡ��Ϊ  1.jpg
	 * @param name �ϴ��������ļ�����
	 * @return	��ʵ����
	 */
	public static String getRealName(String name){
		//��ȡ���һ��"/"
		int index = name.lastIndexOf("\\");
		return name.substring(index+1);
	}
	
	
	/**
	 * ��ȡ�������
	 * @param realName ��ʵ����
	 * @return uuid �������
	 */
	public static String getUUIDName(String realName){
		//realname  ������  1.jpg   Ҳ������  1
		//��ȡ��׺��
		int index = realName.lastIndexOf(".");
		if(index==-1){
			return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		}else{
			return UUID.randomUUID().toString().replace("-", "").toUpperCase()+realName.substring(index);
		}
	}
	
	
	/**
	 * ��ȡ�ļ�Ŀ¼,���Ի�ȡ256�����Ŀ¼
	 * @return ���Ŀ¼
	 */
	public static String getDir(){
		String s="0123456789ABCDEF";
		Random r = new Random();
		return "/"+s.charAt(r.nextInt(16))+"/"+s.charAt(r.nextInt(16));
	}
	
	public static void main(String[] args) {
		//String s="G:\\day17-������ǿ\\resource\\1.jpg";
		String s="1.jgp";
		String realName = getRealName(s);
		System.out.println(realName);
		
		String uuidName = getUUIDName(realName);
		System.out.println(uuidName);
		
		String dir = getDir();
		System.out.println(dir);
	}
}