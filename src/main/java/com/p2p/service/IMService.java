package com.p2p.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.p2p.bean.msg.Message;

public class IMService {
	  
	public  static Map<String, Socket> socMap = new HashMap<String, Socket>();
	  
	  public IMService(){
		  try {
				
		  @SuppressWarnings("resource")
		final ServerSocket serverSocket  =  new ServerSocket(9958);
		  while (true) {
				Socket  socket = serverSocket.accept();
				 ExecutorService executorService = Executors.newFixedThreadPool(3);
				executorService.execute(new Task(socket));
			}
		 
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 
	  }
	  
	  public static void main(String[] args) {
		 new IMService();
	}
	  
	 class Task implements Runnable{
		   public Socket socket ;
		   public Task(Socket socket){
			    this.socket = socket;
		   }
		 public void run(){
			 while (true) {
				 try {
						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						String line = reader.readLine();
						  Message msg=   JSON.parseObject(line, Message.class);
						  if("regist".equals(msg.getType())){
							  socMap.put(msg.getFrom(), socket);
							  System.out.println(socMap);
							  Message message = new Message();
							    message.setId(0);
								message.setFrom(msg.getFrom());
								message.setTo("server");
								message.setContent("registSuccess");
								message.setType("regist");
								writer.write(JSONObject.toJSONString(message)+"\n");
								writer.flush();
						  }else{
					BufferedWriter writerResponse = new BufferedWriter(new OutputStreamWriter(socMap.get(msg.getTo()).getOutputStream()));
					            System.out.println(JSONObject.toJSONString(msg));
					            writerResponse.write(JSONObject.toJSONString(msg)+"\n");
					            writerResponse.flush(); 
						  }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			
			 
			 
		 }
		 
		
		 
	 }
	
}
