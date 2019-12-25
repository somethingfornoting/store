package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.daoImp.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;

public class UserServiceImp implements UserService {

	@Override
	public void userRegist(User user) throws SQLException {
		UserDao UserDao=new UserDaoImp();
		UserDao.userRegist(user);
		
	}

	@Override
	public User userLogin(User user) throws SQLException {
		UserDao UserDao=new UserDaoImp();
		User uu=UserDao.userLogin(user);
		if(uu==null) {
			throw new RuntimeException("密码不正确");
		}else return uu;
		
	}

}
