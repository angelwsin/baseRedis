package com.p2p.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.p2p.DatabaseContextHolder;
import com.p2p.annotation.DataSource;
import com.p2p.bean.user.User;
//注意使用openSession才能生效
@Repository
public class UserDaoImpl implements UserDao {

	     @Resource
	     SessionFactory sessionFactory;
	public void save(User user) {
		// TODO Auto-generated method stub
		 sessionFactory.openSession().save(user);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findUsers() {
		// TODO Auto-generated method stub
		return (List<User>)sessionFactory.openSession().createQuery("from User").list();
	}
}
