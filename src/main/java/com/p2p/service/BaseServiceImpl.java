package com.p2p.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.redis.BaseDao;


@Service
public class BaseServiceImpl<T> implements BaseService<T>{

	  @Resource(name="baseDao")
	  private BaseDao<T> baseDao;
	public long save(T obj,Map<String, String> map) {
		// TODO Auto-generated method stub
		return baseDao.save(obj, map,"data");
	}
	
	public List<T> getList(String keys) {
		// TODO Auto-generated method stub
		return baseDao.getList(keys);
	}
	
	
}
