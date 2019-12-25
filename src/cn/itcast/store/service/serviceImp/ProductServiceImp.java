package cn.itcast.store.service.serviceImp;

import java.util.List;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.dao.daoImp.ProductDaoImp;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;

public class ProductServiceImp implements ProductService {
	ProductDao ProductDao=new ProductDaoImp();
	@Override
	public List<Product> findHots() throws Exception {
		// TODO 自动生成的方法存根
		return ProductDao.findHots();
	}

	@Override
	public List<Product> findNews() throws Exception {
		// TODO 自动生成的方法存根
		return ProductDao.findNews();
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		// TODO 自动生成的方法存根
		return ProductDao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception{
		int totalRecords=ProductDao.findTotalRecords(cid);
//		System.out.println(totalRecords);
		PageModel pm=new PageModel(curNum,totalRecords,12);
		List list=ProductDao.findProductsByCidWithPage(cid,pm.getStartIndex(),pm.getPageSize());
//		System.out.println(pm.getStartIndex());
//		System.out.println(pm.getPageSize());
		pm.setList(list);
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
		
		return pm;
	}

}
