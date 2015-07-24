import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.alibaba.fastjson.JSONObject;
import com.p2p.bean.msg.Message;



public class ClientTest {
	
	  @SuppressWarnings("resource")
	public static void main(String[] args) {
		  try {
				Socket socket = new Socket("localhost", 9958);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//				 new Thread(){
//						public void run() {
//							try {
//								 while (true) {
//									 System.out.println(reader.readLine());
//									} 		
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//					}.start();
				Message message = new Message();
				message.setId(0);
				message.setFrom("");
				message.setTo("");
				message.setContent("2regist");
				message.setType("regist");
				   writer.write(JSONObject.toJSONString(message)+"\n");
				   writer.flush();
				
				        
							Message msg = new Message();
							msg.setId(0);
							msg.setFrom("");
							msg.setTo("");
							msg.setContent("msg");
							msg.setType("msg");
						  
							writer.write(JSONObject.toJSONString(msg)+"\n");
					    	writer.flush();
						  
						
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	  
		  
		
}
