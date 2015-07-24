import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.p2p.bean.msg.Message;


public class ServerTest {
	
	     
	    	 @SuppressWarnings("resource")
			public static void main(String[] args) {
	    		 try {
	 				final ServerSocket serverSocket  =  new ServerSocket(9958);
	 				 final Map<String, Socket> socMap = new HashMap<String, Socket>();
	 				new Thread(){
	 					public void run() {
	 						Socket socket = null;
	 						while (true) {
	 							   try {
	 					    socket = serverSocket.accept();
	 						 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	 						 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	 								String line = reader.readLine();
	 								 System.out.println(line);
	 								  Message msg=   JSON.parseObject(line, Message.class);
	 								  if("regist".equals(msg.getType())){
	 									 socMap.put(msg.getFrom(), socket);
	 								  }
	 								reader.close();
	 								writer.close();
	 							} catch (Exception e) {
	 								// TODO Auto-generated catch block
	 								e.printStackTrace();
	 							}
	 								
	 							
	 						}
	 					}
	 					
	 					
	 				}.start();
	 				 
//	 				new Thread(){
//	 					public void run() {
//	 						Socket socket = null;
//	 						while (true) {
//	 							   try {
//	 					    socket = serverSocket.accept();
//	 						 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//	 						 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//	 								String line = reader.readLine();
//	 								while (!"bye".equals(line)) {
//	 									System.out.println(line);
//										writer.write("server " + line+"\n");
//										writer.flush();
//										line = reader.readLine();
//									}
//	 									  
//	 								  
//	 								reader.close();
//	 								writer.close();
//	 							} catch (Exception e) {
//	 								// TODO Auto-generated catch block
//	 								e.printStackTrace();
//	 							}
//	 								
//	 							
//	 						}
//	 					}
//	 					
//	 					
//	 				}.start();
//	 				new Thread(){
//	 					public void run() {
//	 						Socket socket = null;
//	 						while (true) {
//	 							   try {
//	 					    socket = serverSocket.accept();
//	 						 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//	 						 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//	 								String line = reader.readLine();
//	 								while (!"bye".equals(line)) {
//	 									System.out.println(line);
//										writer.write("server " + line+"\n");
//										writer.flush();
//										line = reader.readLine();
//									}
//	 									  
//	 								  
//	 								reader.close();
//	 								writer.close();
//	 							} catch (Exception e) {
//	 								// TODO Auto-generated catch block
//	 								e.printStackTrace();
//	 							}
//	 								
//	 							
//	 						}
//	 					}
//	 					
//	 					
//	 				}.start();
//	 				
 			} catch (IOException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
			}
	    	
	    	 
	     
}
