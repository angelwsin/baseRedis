package com.p2p.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.p2p.bean.post.Post;
import com.p2p.dao.redis.CommentDao;
import com.p2p.dao.redis.PostDao;



@Controller
@RequestMapping("/post")
public class PostAction  extends DefaultAction{

	@Resource
    private PostDao postDao;
	@Resource
	private CommentDao commentDao;
	
	
	  @RequestMapping(value="/add",method=RequestMethod.GET)
	   public String add(){
			   return "post/post_add";
		  
		  
	   }
	  @RequestMapping(value="/edit",method=RequestMethod.POST)
	   public void edit(Post post,HttpServletRequest request,HttpServletResponse response){    
    	   post.setTime(new Date());
    	  postDao.saveHash(post, "post:id", "slug.to.id",post.getSlug(),"post:list","post:data:");
		   try {
				response.sendRedirect(request.getContextPath()+"/post/list");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
	   }
	  
	  
	  @RequestMapping(value="/list")
	 public String list(HttpServletRequest request){
		 // List<Post> list = postDao.getList("Post:*:data");
		  List<Post> list = postDao.getObjectPage(1, 10, "post:list", "post:data:");
		  request.setAttribute("postList", list);
		  return "post/post_list";
	 }
	  
	  @RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	  public String detail(HttpServletRequest request,@PathVariable("id") int id){
		        
		    request.setAttribute("post",  postDao.getHash("post:data:"+id));
		    request.setAttribute("comments", commentDao.getListObject("post:"+id+":comment",0,-1));
		    if(request.getParameter("type")==null){
		    	// request.setAttribute("view", postDao.incr(id, "page.view"));
		    	request.setAttribute("view", (long)postDao.zIncr("posts:page.view", id));
		    }else {
				//request.setAttribute("view", postDao.getIncr(id+":page.view"));
		    	request.setAttribute("view", (long)postDao.getZincr("posts:page.view", id));
			}
		   
		  return "post/post_detail";
	  }
	
	
	
	   
	   
}
