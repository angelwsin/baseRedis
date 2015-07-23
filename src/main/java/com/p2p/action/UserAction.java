package com.p2p.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.p2p.bean.user.User;
import com.p2p.service.UserService;

/**
 * 用户对象action
 * 
 * @package com.p2p.action
 * @author Ready
 * @date 2014年12月26日
 * @since
 * 
 */
@Controller
@RequestMapping(value="/user")
public class UserAction   extends DefaultAction{
	    @Resource
	    UserService userService;
	  
	    
	    private User user;
	     @RequestMapping(value="/list",method=RequestMethod.GET)
	     public String  list(HttpServletRequest request){
	    	 request.setAttribute("userList", userService.findUsers());
	    	 return "user/user_info";
	     }
	     @RequestMapping(value="/add",method=RequestMethod.GET)
	     public String  add(){
	    	 return "user_add";
	     }
	     @RequestMapping(value="/edit",method=RequestMethod.POST)
	     public void edit(HttpServletResponse response){
	    	  userService.save(user);
	    	  try {
	    		  response.sendRedirect("/fenbushi/user/list");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 
	     }
		
		
		public User getUser() {
			return user;
		}

		
		public void setUser(User user) {
			this.user = user;
		}
		
		
		
}