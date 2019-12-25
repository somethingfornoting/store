package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.dao.daoImp.CategoryDaoImp;
import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;

public class CategoryServiceImp implements CategoryService {

	@Override
	public List<Category> getAllCats() throws SQLException {
		CategoryDao CategoryDao=new CategoryDaoImp();
		return CategoryDao.getAllCats();
	}

}
