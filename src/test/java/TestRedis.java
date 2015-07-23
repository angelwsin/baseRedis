import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.expr.NewArray;

import javax.annotation.Resource;

import org.apache.logging.log4j.core.config.json.JsonConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.p2p.bean.comment.Comment;
import com.p2p.bean.post.Post;
import com.p2p.bean.user.User;
import com.p2p.dao.redis.CommentDao;
import com.p2p.dao.redis.PostDao;
import com.p2p.dao.redis.UserDao;
import com.p2p.service.PostService;
import com.p2p.util.ObjectToMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = false)
public class TestRedis {

	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	@Resource(name = "redisWinTemplate")
	private RedisTemplate<Serializable, Serializable> redisWinTemplateLoc;
	@Resource(name = "userDaoRedis")
	private UserDao userDao;
	@Resource
	private PostDao postDao;
	@Resource
	PostService postService;
	@Resource
	private CommentDao commentDao;

	//@Test
	public void testRedis() {
		System.out.println(redisTemplate.getClass());
	}

	//@Test
	public void testSave() {
		User u = new User();
		u.setId(20);
		Map<String, String> map = new HashMap<String, String>();
		map.put("INCR", "userID");
		u.setUsername("hedandan");
		// long id= userDao.save(u,map);
		// System.out.println(id);
		// User user= userDao.get(5,null);
		// System.out.println(user.getUsername());
		//  System.out.println(postService.getList("Post:*:data").get(0).getAuthor()); 
		//   System.out.println(postService.get(7, null))
	}

	//@Test
	public void testSavePost() {
		Post ps = new Post();
		ps.setAuthor("四六級");
		ps.setContent("d");
		ps.setTitle("d");
		ps.setTime(new Date());
		Map<String, String> map = new HashMap<String, String>();
		map.put("INCR", "userID");
	     // postService.save(ps, map);
		// postDao.saveHash(ps, "posts:count", "slug.to.id","slugtest","posts:");
		//  Post post = postDao.getHash("slug.to.id","slugtest","posts:1");
		//  System.out.println(post.getId());
		//   postDao.editHash(1, "slug.to.id","slugtest","slugt");
		//   postDao.saveList("posts:list", "1".getBytes());
		//System.out.println(postDao.getObjectPage(1, 10, "posts:list", "posts:").get(5).getAuthor());
		//postDao.save(ps, map, "data");
		//System.out.println(postDao.getList("Post:*:data").get(0).getAuthor());
		postDao.saveSet("post:1:tags", "章撒登錄".getBytes());
	}

	//@Test
	public void testComment() {
		Comment comment = new Comment();
		comment.setAuthor("张三");
		comment.setContent("浸提提");
		comment.setTime(new Date());
		//commentDao.saveList("post:1:comment", JSON.toJSONString(comment).getBytes());
		System.out.println(commentDao.getListObject("post:1:comment",0,-1).get(0).getContent());
	}

	// @Test
	public void testGetUser() {
		User user = userDao.findUserByID(19);
		System.out.println(user.getUsername());
	}

	// @Test
	public void testwin() {
		System.out.println(userDao.getClass());
		User user = new User();
		user.setId(48);
		user.setUsername("wangwu");
		userDao.saveWinMapUser(user);
	}

	//@Test
	public void testL() {
		System.out.println(redisWinTemplateLoc.getClass());
		ValueOperations<Serializable, Serializable> op = redisWinTemplateLoc.opsForValue();
		System.out.println(op == null);
	}

	@SuppressWarnings("unchecked")
	// @Test
	public void objectTOMap() {
		Post post = postDao.get(8);
		String str = JSONObject.toJSONString(post);
		HashMap<String, String> map = JSON.parseObject(JSONObject.toJSONString(post), HashMap.class);
		System.out.println(map.toString());
		System.out.println(ObjectToMap.objectToMap(post));
	}
	
	@Test
	public void testZ(){
		System.out.println(postDao.getHash("post:data:5").getContent());
		 List<Post> list = postDao.getObjectPage(1, 10, "post:list","post:data:");
		  for(Post p:list){
			  System.out.println(p.getContent());
		  }
		  
	}
}
