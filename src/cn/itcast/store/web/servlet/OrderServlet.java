package cn.itcast.store.web.servlet;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Cart;
import cn.itcast.store.domain.CartItem;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.service.serviceImp.OrderServiceImp;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

public class OrderServlet extends BaseServlet {
	// saveOrder  将购物车中的信息以订单的形式保存
	public String saveOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//确认用户登录状态
		User user=(User)req.getSession().getAttribute("loginUser");
		if(null==user){
			req.setAttribute("msg", "请登录之后在下单");
			return "/jsp/info.jsp";
		}
		//获取购物车
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		//创建订单对象,为订单对象赋值
		Order order=new Order();
		order.setOid(UUIDUtils.getCode());
		order.setOrdertime(new Date());
		order.setTotal(cart.getTotal());
		order.setState(1);
		order.setUser(user);
		//遍历购物项的同时,创建订单项,为订单项赋值
		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem=new OrderItem();
			orderItem.setItemid(UUIDUtils.getCode());
			orderItem.setQuantity(item.getNum());
			orderItem.setTotal(item.getSubTotal());
			orderItem.setProduct(item.getProduct());
			
			//设置当前的订单项属于哪个订单:程序的角度体检订单项和订单对应关系
			orderItem.setOrder(order);
			order.getList().add(orderItem);
		}
		//调用业务层功能:保存订单
		OrderService OrderService=new OrderServiceImp();
		//将订单数据,用户的数据,订单下所有的订单项都传递到了service层
		OrderService.saveOrder(order);
		//清空购物车
		cart.clearCart();
		//将订单放入request
		req.setAttribute("order", order);
		//转发/jsp/order_info.jsp
		return "/jsp/order_info.jsp";
	}
	
	//findMyOrdersWithPage
	public String findMyOrdersWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取用户信息
		User user=(User)req.getSession().getAttribute("loginUser");
		
		//获取当前页
		int curNum=Integer.parseInt(req.getParameter("num"));
		//调用业务层功能:查询当前用户订单信息,返回PageModel
		OrderService OrderService=new OrderServiceImp();
		//SELECT * FROM orders WHERE uid=? limit ? , ? 
		//PageModel:1_分页参数 2_url  3_当前用户的当前页的订单(集合) ,每笔订单上对应的订单项,以及订单项对应的商品信息
		PageModel pm=OrderService.findMyOrdersWithPage(user,curNum);
	
		//将PageModel放入request
		req.setAttribute("page", pm);
		//转发到/jsp/order_list.jsp
		return "/jsp/order_list.jsp";
		
	}
	
	//findOrderByOid
	public String findOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取到订单oid
		String oid=req.getParameter("oid");
		//调用业务层功能:根据订单编号查询订单信息
		OrderService OrderService=new OrderServiceImp();
		Order order=OrderService.findOrderByOid(oid);
		// 将订单放入request
		req.setAttribute("order", order);
		// 转发到/jsp/order_info.jsp
		return "/jsp/order_info.jsp";
	}
	
	//payOrder
	public String payOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取订单oid,收货人地址,姓名,电话,银行
		String oid=req.getParameter("oid");
		String address=req.getParameter("address");
		String name=req.getParameter("name");
		String telephone=req.getParameter("telephone");
		//更新订单上收货人的地址,姓名,电话
		OrderService OrderService=new OrderServiceImp();
		Order order=OrderService.findOrderByOid(oid);
		order.setName(name);
		order.setTelephone(telephone);
		order.setAddress(address);
		order.setState(2);
		OrderService.updateOrder(order);
		
		req.setAttribute("msg", "支付成功！订单号："+oid );
		return "/jsp/info.jsp";
	}
	
	
}
