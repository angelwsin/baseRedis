package com.p2p.dao;

import java.util.List;

import com.p2p.annotation.DataSource;
import com.p2p.bean.user.User;


public interface UserDao {
	
	@DataSource("dataSourceMaster")
	  public void save(User user);
	 @DataSource("dataSourceSlave")
	  public  List<User>  findUsers();
}
