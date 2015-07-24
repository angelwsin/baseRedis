package com.p2p.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.p2p.bean.msg.Message;

public class IMService {
	  
	
	  
	  public IMService(){
		  try {
			final ServerSocket serverSocket  =  new ServerSocket(9958);
				new Thread(){
					public void run() {
						Socket socket = null;
						while (true) {
							   try {
					    socket = serverSocket.accept();
						 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
								String line = reader.readLine();
								  Message msg=   JSON.parseObject(line, Message.class);
								  if("regist".equals(msg.getType())){
									  Message message = new Message();
									    message.setId(0);
										message.setFrom(msg.getFrom());
										message.setTo("server");
										message.setContent("registSuccess");
										message.setType("regist");
										writer.write(JSONObject.toJSONString(message)+"\n");
										writer.flush();
								  }else{
									  writer.write(JSONObject.toJSONString(msg)+"\n");
										writer.flush(); 
								  }
								  
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					
				}.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public static void main(String[] args) {
		 new IMService();
	}
	
}
