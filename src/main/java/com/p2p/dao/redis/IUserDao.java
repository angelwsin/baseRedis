package com.p2p.dao.redis;

import java.io.Serializable;

import com.p2p.bean.user.User;

public interface IUserDao {
	
	public User findUserByID(Serializable id);
}
