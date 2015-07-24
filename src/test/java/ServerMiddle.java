import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.alibaba.fastjson.JSONObject;
import com.p2p.bean.msg.Message;



public class ServerMiddle extends Thread {
	   private Socket formSocket  ;
	   private Socket toSocket;
	   private Message message;
	
	  public ServerMiddle(Socket formSocket,Socket toSocket,Message message){
		    this.formSocket =formSocket;
		    this.toSocket = toSocket;
		    this.message = message;
		    
		    
	  }
	  
	  public void run() {
		
				   try {
			 BufferedReader reader = new BufferedReader(new InputStreamReader(formSocket.getInputStream()));
			 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(toSocket.getOutputStream()));
						System.out.println(JSONObject.toJSONString(message));
						writer.write(JSONObject.toJSONString(message)+"\n");
						writer.flush();
					reader.close();
					writer.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
		
		}
	
}
