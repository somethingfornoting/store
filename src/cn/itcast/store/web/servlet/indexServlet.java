package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class indexServlet
 */
public class indexServlet extends BaseServlet {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取全部分类信息
		CategoryService CategoryService=new CategoryServiceImp();
		List<Category> list=CategoryService.getAllCats();
		//将返回集合放入request
		request.setAttribute("allCats", list);
		
		ProductService ProductService=new ProductServiceImp(); 
		List<Product> list01=ProductService.findHots();
		List<Product> list02=ProductService.findNews();
		request.setAttribute("hots", list01);
		request.setAttribute("news", list02);
		//转发到真实页面
		return "/jsp/index.jsp";
	}

}
