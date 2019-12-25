package cn.itcast.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.omg.CORBA.OBJECT_NOT_EXIST;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;

public class UserDaoImp implements UserDao {

	@Override
	public void userRegist(User user)throws SQLException {
		String sql="INSERT INTO user(username,password,name)VALUES(?,?,?)";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		Object[] params= {user.getUsername(),user.getPassword(),user.getName()};	
		qr.update(sql,params);
		
	}

	@Override
	public User userLogin(User user) throws SQLException {
		String sql="select * from user where username=? and password=?";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql,new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
	}

}
