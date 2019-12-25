package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.UserServiceImp;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	
   
	public String registUI(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, SQLException {
		
		CategoryService CategoryService=new CategoryServiceImp();
		List<Category> list=CategoryService.getAllCats();
		//将返回集合放入request
		request.setAttribute("allCats", list);
	return "/jsp/register.jsp";
	}
	
	public String userRegist(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//接受表单数据
		Map<String, String[]> map = request.getParameterMap();
		User user=new User();
		MyBeanUtils.populate(user, map);
		
		UserService UserService=new UserServiceImp();
		try {
			//注册成功 跳转 
			UserService.userRegist(user);
			request.setAttribute("msg","用户注册成功");
			
		} catch (Exception e) {
			//注册失败 跳转
			request.setAttribute("msg","用户注册失败");
			
		}
	return "/jsp/info.jsp";
	}

	public String loginUI(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, SQLException {
		CategoryService CategoryService=new CategoryServiceImp();
		List<Category> list=CategoryService.getAllCats();
		//将返回集合放入request
		request.setAttribute("allCats", list);
	return "/jsp/login.jsp";
	}
	//userLogin
	public String userLogin(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//接受表单数据
		User user=new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		UserService UserService=new UserServiceImp();
		User user02=new User();
		try {
			
			user02=UserService.userLogin(user);
			request.getSession().setAttribute("loginUser", user02);
			response.sendRedirect("/Store/index.jsp");
			return null;
		} catch (Exception e) {
			
			String msg = e.getMessage();
			System.out.println(msg);
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}

	}
	
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  //清除session
			request.getSession().invalidate();
		  //重新定向到首页
		  response.sendRedirect("/Store/index.jsp");
		  return null;	
	}
}
