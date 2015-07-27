package com.p2p.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.p2p.bean.msg.Message;
import com.p2p.bean.user.User;
import com.p2p.service.IMService;


@Controller
@RequestMapping(value="/im")
public class IMAction {
	    public  static Map<String, Socket> socClientMap = new HashMap<String, Socket>();
	     Socket socket = null;
	     BufferedWriter writer ;
	     BufferedReader reader;
	       @RequestMapping(value="/view",method=RequestMethod.GET)
	       public String view(Message message,HttpServletRequest request){
	    	   
			try {
				socket = new Socket("localhost", 9958);
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    message.setId(0);
			    User user = (User) request.getSession().getAttribute("user");
			    message.setFrom(user.getUsername());
			    message.setTo("admin");
			    message.setContent("regist");
				message.setType("regist");
				writer.write(JSONObject.toJSONString(message)+"\n");
				writer.flush();
				Message msg = JSON.parseObject(reader.readLine(),Message.class);
				if("registSuccess".equals(msg.getContent())){
					socClientMap.put(message.getFrom(), socket);
				}
				 request.setAttribute("user", user);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	    	   return "msg/msg_add";
	       }
	       
	       @RequestMapping(value="/send")
	       @ResponseBody
	       public void send(Message message,HttpServletRequest request){
	    	   User user = (User) request.getSession().getAttribute("user");
	    	    socket =  socClientMap.get(user.getUsername());
	    	   
	    	    try {
					writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					writer.write(JSONObject.toJSONString(message)+"\n");
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	     
	    	    
	       }
	       
	       @RequestMapping(value="/readMsg/{to}")
	      // @ResponseBody
	       public String readMsg(@PathVariable("to") String to,final HttpServletRequest request){
	    	    socket = socClientMap.get(to);
	    	    new Thread(){
	    	    	public void run() {
	    	    		while (true) {
	    	    			try {
								reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								 Message message= JSON.parseObject(reader.readLine(), Message.class);
								 System.out.println(message.getContent());
								 request.setAttribute("msg", message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
	    	    		
	    	    	}
	    	    	
					
	    	    }.start();
	    	    
	    	    return "msg/msg_show";
	       }
}
