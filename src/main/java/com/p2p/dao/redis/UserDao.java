 package com.p2p.dao.redis;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.p2p.bean.user.User;

@Repository("userDaoRedis")
public class UserDao extends BaseDao<User> implements IUserDao{
	
	
	 
	
	public User findUserByID(Serializable id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				BoundHashOperations<Serializable, String, String> hashOperations =   redisTemplate.boundHashOps(id);
				Map<String, String> user =hashOperations.entries();
				
				User  u  =new User();
				u.setId(Integer.valueOf(id+""));
				u.setUsername(user.get("username"));
				return u;
	}

	public void saveWinMapUser(User user){
		BoundHashOperations<Serializable, String, String> hashOperations =   redisWinTemplate.boundHashOps(user.getId());
		hashOperations.put("username", user.getUsername()); 
	}



	
	
	
	

}
