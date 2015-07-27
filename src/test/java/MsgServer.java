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
import com.alibaba.fastjson.JSONObject;
import com.p2p.bean.msg.Message;



public class MsgServer {
	public  static Map<String, Socket> socMap = new HashMap<String, Socket>();
	  public static void main(String[] args) {
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
						while (!"bye".equals(line)) {
						 Message msg=   JSON.parseObject(line, Message.class);
						 Message message = new Message();
						    message.setId(0);
							message.setFrom(msg.getFrom());
							message.setTo("server");
							message.setContent("registSuccess");
							message.setType("regist");
							writer.write(JSONObject.toJSONString(message)+"\n");
							writer.flush();
							line = reader.readLine();
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
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
  	
		
	}
