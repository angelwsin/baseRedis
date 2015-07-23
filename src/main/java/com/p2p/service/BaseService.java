package com.p2p.service;

import java.util.List;
import java.util.Map;




public interface  BaseService<T> {
	
	  public long save(T obj,Map<String, String> map);
	 
	  public List<T> getList(final String keys);
}
