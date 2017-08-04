package com.liu.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.liu.constant.Constant;
import com.liu.domain.Category;
import com.liu.domain.Product;
import com.liu.service.ProductService;
import com.liu.utils.BeanFactory;
import com.liu.utils.UUIDUtils;
import com.liu.utils.UploadUtils;

/**
 * ������Ʒ
 */
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("������Ʒ");
		try {
			//0.ʹ��fileuload����ͼƬ�ͽ���Ʒ����Ϣ����map��
			//0.1 ����map �����Ʒ����Ϣ
			Map<String,Object> map=new HashMap<String,Object>();
			
			//0.2 ���������ļ���� (������ʱ�ļ��Ĵ�С��λ��)
			DiskFileItemFactory factory = new DiskFileItemFactory();
			
			//0.3 ���������ϴ�����
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//0.4 ����request
			List<FileItem> list = upload.parseRequest(request);
			
			//0.5����list ��ȡÿһ���ļ���
			for (FileItem fi : list) {
				//0.6��ȡname����ֵ
				String key = fi.getFieldName();
				
				//0.7�ж��Ƿ�����ͨ���ϴ����
				if(fi.isFormField()){
					//��ͨ
					map.put(key, fi.getString("utf-8"));
				}else{
					//�ļ�
					//a.��ȡ�ļ�������  1.jpg
					String name = fi.getName();
					
					//b.��ȡ�ļ���ʵ���� 1.jpg
					String realName = UploadUtils.getRealName(name);
					
					//c.��ȡ�ļ����������  12312312434234.jpg
					String uuidName = UploadUtils.getUUIDName(realName);
					
					//d.��ȡ���Ŀ¼ /a/3
					String dir = UploadUtils.getDir();
					
					//e.��ȡ�ļ�����(������)
					InputStream is = fi.getInputStream();
					
					//f.���������
					//��ȡproductsĿ¼����ʵ·��
					String productPath = getServletContext().getRealPath("/products");
					
					//�������Ŀ¼
					File dirFile = new File(productPath,dir);
					if(!dirFile.exists()){
						dirFile.mkdirs();
					}
					
					// d:/tomcat/webapps/store/prouduct/a/3/12312312434234.jpg
					FileOutputStream os = new FileOutputStream(new File(dirFile,uuidName));
					
					//g.�Կ���
					IOUtils.copy(is, os);
					
					//h.�ͷ���Դ
					os.close();
					is.close();
					
					//i.ɾ����ʱ�ļ�
					fi.delete();
					
					//j.����Ʒ��·������map��   prouduct/a/3/12312312434234.jpg
					map.put(key, "products"+dir+"/"+uuidName);
				}
			}
			
			//1.��װproduct����
			Product p = new Product();
			//1.1.�ֶ����� pid
			map.put("pid", UUIDUtils.getId());
			
			//1.2.�ֶ����� pdate
			map.put("pdate", new Date());
			
			//1.3.�ֶ����� pflag  �ϼ�
			map.put("pflag", Constant.PRODUCT_IS_UP);
			
			//1.4.ʹ��beanutils��װ
			BeanUtils.populate(p, map);
			
			//1.5.�ֶ����� category
			Category c = new Category();
			c.setCid((String)map.get("cid"));
			p.setCategory(c);
			
			//2.����service ��ɱ���
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.save(p);
			
			//3.�ض���
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("������Ʒʧ��");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
