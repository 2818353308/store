package com.liu.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liu.domain.User;

public class PrivilegeFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request2, ServletResponse response2,
			FilterChain chain) throws IOException, ServletException {
		//1.ǿת
		HttpServletRequest request=(HttpServletRequest) request2;
		HttpServletResponse response=(HttpServletResponse) response2;
		//2.�߼�
		//��session�л�ȡ�û�
		User user =  (User) request.getSession().getAttribute("user");
		if(user == null){
			//δ��¼
			request.setAttribute("msg", "���¼");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);;
			return;
		}
		//3.����
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
