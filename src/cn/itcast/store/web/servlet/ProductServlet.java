package cn.itcast.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	

public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
	// TODO 自动生成的方法存根
	String pid=request.getParameter("pid");
	//System.out.println(pid);
	ProductService ProductService=new ProductServiceImp();
	Product product=ProductService.findProductByPid(pid);
	request.setAttribute("product", product);
	return "/jsp/product_info.jsp";
	
}

	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cid=request.getParameter("cid");
		
		int curNum=Integer.parseInt(request.getParameter("num"));
		//System.out.println(cid);
		//System.out.println(curNum);
		ProductService ProductService=new ProductServiceImp();
		PageModel pm=ProductService.findProductsByCidWithPage(cid,curNum);
		request.setAttribute("page", pm);
		return "/jsp/product_list.jsp";
	}
}
