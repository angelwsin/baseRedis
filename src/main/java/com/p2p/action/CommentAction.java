package com.p2p.action;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.core.config.json.JsonConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.p2p.bean.comment.Comment;
import com.p2p.dao.redis.CommentDao;


@Controller
@RequestMapping(value="/comment")
public class CommentAction {
	@Resource
	private CommentDao commentDao;
	@RequestMapping(value="/add/{postid}")
	public String add(@PathVariable("postid") int postid,HttpServletResponse response,HttpServletRequest request,Comment c){
		 c.setTime(new Date());
		commentDao.saveList("post:"+postid+":comment", JSON.toJSONString(c).getBytes());
		try {
			response.sendRedirect(request.getContextPath()+"/post/detail/"+postid+"?type=comment");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
