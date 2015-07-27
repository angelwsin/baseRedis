import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.p2p.bean.msg.Message;



public class ServerMiddle extends Thread {
	   private Socket socket;
	
	  public ServerMiddle(Socket socket){
		    this.socket = socket;
	  }
	  
	  public void run() {
		
				   try {
			 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			 String line = reader.readLine();
			  Message msg=   JSON.parseObject(line, Message.class);
			  writer.write(line+"\n");
			 writer.flush();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
		
		}
	
}
