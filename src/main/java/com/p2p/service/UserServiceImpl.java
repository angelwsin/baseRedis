package com.p2p.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.p2p.bean.user.User;
import com.p2p.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

	 @Resource
	 UserDao userDao;
	 
	 
	public void save(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}
	public List<User> findUsers() {
		// TODO Auto-generated method stub
		return userDao.findUsers();
	}

	
}
