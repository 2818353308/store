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
		//1.强转
		HttpServletRequest request=(HttpServletRequest) request2;
		HttpServletResponse response=(HttpServletResponse) response2;
		//2.逻辑
		//从session中获取用户
		User user =  (User) request.getSession().getAttribute("user");
		if(user == null){
			//未登录
			request.setAttribute("msg", "请登录");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);;
			return;
		}
		//3.放行
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
