package com.p2p.service;

import java.util.List;

import com.p2p.annotation.DataSource;
import com.p2p.bean.user.User;


public interface UserService {
	
	  public void save(User user);
	 
	  public List<User> findUsers();
	
}
