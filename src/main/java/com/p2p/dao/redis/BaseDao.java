package com.p2p.dao.redis;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.p2p.util.ObjectToMap;

import freemarker.core.ReturnInstruction.Return;

@Repository
public class BaseDao<T> {
	
	protected Class<T> clazz;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDao() {
		Class type = getClass();
		if (type != BaseDao.class) {
			Class parent = type.getSuperclass();
			while (parent != BaseDao.class) {
				parent = (type = parent).getSuperclass();
			}
			Type[] types = ((ParameterizedType) type.getGenericSuperclass()).getActualTypeArguments();
			if (types.length > 0) {
				this.clazz = (Class<T>) types[0];
			}
		}
	}

	public Class<T> getClazz() {
		return this.clazz;
	}

	
	protected RedisTemplate<Serializable, Serializable> redisTemplate;
	protected RedisTemplate<Serializable, Serializable> redisWinTemplate;
	 @Resource(name="redisTemplate")  
	public void setRedisTemplate(
			RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
		redisSerializer= redisTemplate.getStringSerializer();
	}
   
	 
	
	public RedisTemplate<Serializable, Serializable> getRedisWinTemplate() {
		return redisWinTemplate;
	}
	 @Resource(name="redisWinTemplate")
	public void setRedisWinTemplate(
			RedisTemplate<Serializable, Serializable> redisWinTemplate) {
		this.redisWinTemplate = redisWinTemplate;
		setRedisWinSerializer(redisWinTemplate.getStringSerializer());
	}



	 protected RedisSerializer<String> redisSerializer;
	
	 protected RedisSerializer<String> redisWinSerializer;
	public RedisSerializer<String> getRedisWinSerializer() {
		return redisWinSerializer;
	}



	public void setRedisWinSerializer(RedisSerializer<String> redisWinSerializer) {
		this.redisWinSerializer = redisWinSerializer;
	}
	
	public long save(final T obj,final Map<String, String> map,final String suffix){
		 return redisTemplate.execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				
				long id =connection.incr(redisSerializer.serialize( map.get("INCR")));
		           try {
		           Class<?> clazz = obj.getClass();
		           Method setID= clazz.getDeclaredMethod("setId", int.class);
		             setID.invoke(obj, (int)id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				connection.set(redisSerializer.serialize(obj.getClass().getSimpleName()+":"+id+":"+suffix), redisSerializer.serialize(JSON.toJSONString(obj)));
				return id;
			}
		});
	}
	
	public T get(final int id){
		 return redisTemplate.execute(new RedisCallback<T>() {

			public T doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
			 byte[] obj= connection.get(redisSerializer.serialize(clazz.getSimpleName()+":"+id+":data"));

				
					try {
						return  (T) JSON.parseObject(new String(obj,"utf-8"), clazz);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
			}
			
		});
	}
	
	public List<T> getList(final String keys){
		return  redisTemplate.execute(new RedisCallback<List<T>>() {

			public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				List<T> list = new ArrayList<T>();
				 byte[] object = null;
				Set<byte[]>  set = connection.keys(redisSerializer.serialize(keys));
				for(byte[] obj :set){
			    object= connection.get(obj);
			    try {
					list.add( (T) JSON.parseObject(new String(object,"utf-8"), clazz));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				return list;
			}
		});
	}
	
	public long  incr(final int id,final String suffix){
		   return redisTemplate.execute( new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				return   connection.incr(redisSerializer.serialize( clazz.getSimpleName()+":"+id+":"+suffix));
			}
		});
	}
	public long getIncr(final String key){
		  return  redisTemplate.execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				return Long.valueOf(new String(connection.get(redisSerializer.serialize(clazz.getSimpleName()+":"+key))));
			}
		});
	}
	public long saveHash(final T obj,final String idCount,final String ...k){
		return redisTemplate.execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
			long  id = connection.incr(redisSerializer.serialize(idCount));
			 try {
		           Class<?> clazz = obj.getClass();
		           Method setID= clazz.getDeclaredMethod("setId", int.class);
		          setID.invoke(obj, (int)id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			  if(!connection.hSetNX(redisSerializer.serialize(k[0]), redisSerializer.serialize(k[1]), redisSerializer.serialize(id+""))){
				   return  (long) 0;
			  }else {
				 connection.lPush(redisSerializer.serialize(k[2]), redisSerializer.serialize(id+""));
				 connection.hMSet(redisSerializer.serialize(k[3]+id), ObjectToMap.mapTObyte(obj));
			}
				return id;
			}
		});
	}
	
	public T getHash(final String ...k){
		return  redisTemplate.execute(new RedisCallback<T>() {

			public T doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
			 byte[] b=	connection.hGet(redisSerializer.serialize(k[0]), redisSerializer.serialize(k[1]));
			       if(b!=null){
			    	Map<byte[], byte[]>  hs= connection.hGetAll(redisSerializer.serialize(k[2]));
			    	 return JSON.parseObject(JSONObject.toJSONString(ObjectToMap.byteToMap(hs)), clazz);
			       }
				return null;
			}
		});
	}
	public T getHash(final String  hKey){
		return  redisTemplate.execute(new RedisCallback<T>() {

			public T doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub 
			    	Map<byte[], byte[]>  hs= connection.hGetAll(redisSerializer.serialize(hKey));
			    	 return JSON.parseObject(JSONObject.toJSONString(ObjectToMap.byteToMap(hs)), clazz);
			     
			}
		});
	}
	/**
	 * 
	 *
	 * @since 
	 * @param id
	 * @param k hashmap  1.k[0]  2.k[1]old  3.k[2]new
	 * @return
	 */
	public Map<String, String> editHash(final int id,final String ...k){
		
		return redisTemplate.execute(new RedisCallback<Map<String, String>>() {

			public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
	boolean is= connection.hSetNX(redisSerializer.serialize(k[0]), redisSerializer.serialize(k[2]), redisSerializer.serialize(id+""));
				  if(is){
			  connection.hDel(redisSerializer.serialize(k[0]), redisSerializer.serialize(k[1]));
			     return  ObjectToMap.byteToMap(connection.hGetAll(redisSerializer.serialize(k[0])));
				  }
				return null;
			}
		});
	}
	
	public void  saveList(final String key,final byte[] ...b){
		  redisTemplate.execute(new RedisCallback<T>() {
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				 connection.lPush(redisSerializer.serialize(key), b);
				return null;
			}
		});
	}
	
	public List<T> getListObject(final String key,final int begin,final int end){
		 final List<T> list = new ArrayList<T>();
		  return  redisTemplate.execute(new RedisCallback<List<T>>() {

			public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				List<byte[]>	bList=	connection.lRange(redisSerializer.serialize(key), begin, end);
				   for(byte[] b:bList){
					list.add(JSON.parseObject(new String(b),clazz));
					
				   }
				return list;
			}
		});
		
	}
	
	public List<String> getPage(int currentPage,int size,final String key){
		    final int begin = (currentPage-1)*size;
		    final int end = currentPage*size-1;
		  return  redisTemplate.execute(new RedisCallback<List<String>>() {

				public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
					// TODO Auto-generated method stub
					return ObjectToMap.byteToList(connection.lRange(redisSerializer.serialize(key), begin, end));
				}
			});
		
	}
	public List<T> getObjectPage(int currentPage,int size,final String key,final String hkey){
	    final int begin = (currentPage-1)*size;
	    final int end = currentPage*size-1;
	    final List<T> list = new ArrayList<T>();
	  return  redisTemplate.execute(new RedisCallback<List<T>>() {

			public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
			 List<byte[]>	bList=connection.lRange(redisSerializer.serialize(key), begin, end);
			         for(byte[] b:bList){
			        try {
			list.add(JSON.parseObject(JSONObject.toJSONString(ObjectToMap.byteToMap(connection.hGetAll(redisSerializer.serialize(hkey+new String(b,"utf-8"))))), clazz));
					} catch (SerializationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			         }
				return list;
			}
		});
	
}
	
	public void saveSet(final String key,final byte[] ...values){
	
		redisTemplate.execute(new RedisCallback<T>() {	
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				connection.sAdd(redisSerializer.serialize(key), values);
				return null;
			}
		});
	}
	
	public double  zIncr(final String key,final int id){
		    return redisTemplate.execute(new RedisCallback<Double>() {

				public Double doInRedis(RedisConnection connection) throws DataAccessException {
					// TODO Auto-generated method stub
					return connection.zIncrBy(redisSerializer.serialize(key), 1, redisSerializer.serialize(id+""));
				}
			});
	}
	
	public double getZincr(final String key,final int id){
		  return redisTemplate.execute(new RedisCallback<Double>() {

			public Double doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				return connection.zScore(redisSerializer.serialize(key), redisSerializer.serialize(id+""));
			}
		});
	}
	
	public List<T> getHot(final String key,final String hKey,int currentPage,final int size){
		 final int begin = (currentPage-1)*size;
		 final List<T> list = new ArrayList<T>();
		  return redisTemplate.execute(new RedisCallback<List<T>>() {
			public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
		Set<Tuple> tuples = connection.zRevRangeByScoreWithScores(redisSerializer.serialize(key), 0,Double.MAX_VALUE,begin,size);
		         Iterator<Tuple>  tIterator= tuples.iterator();
		         while (tIterator.hasNext()) {
		        	 list.add(JSON.parseObject(JSONObject.toJSONString(ObjectToMap.byteToMap(connection.hGetAll(redisSerializer.serialize(hKey+new String(tIterator.next().getValue()))))), clazz));
				}
				return list;
			}
		});
	}
	
	
}
