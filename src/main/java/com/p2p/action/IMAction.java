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


@Controller
@RequestMapping(value="/im")
public class IMAction {
	public  static Map<String, Socket> socMap = new HashMap<String, Socket>();
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
					socMap.put(message.getFrom(), socket);
				}
				writer.close();
				reader.close();
				
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
	       public void send(Message message){
	    	    socket =  socMap.get(message.getTo());
	    	    System.out.println(socket);
	    	    try {
					writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					writer.write(JSONObject.toJSONString(message)+"\n");
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	     
	    	    
	       }
	       
	       @RequestMapping(value="/readMsg/{to}")
	       @ResponseBody
	       public Message readMsg(@PathVariable("to") String to){
	    	    socket = socMap.get(to);
	    	    Message message = null;
	    	    try {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println(reader.readLine());
					message= JSON.parseObject(reader.readLine(), Message.class);
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	    return message;
	       }
}
