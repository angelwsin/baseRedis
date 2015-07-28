package com.p2p.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.p2p.bean.msg.Message;

@Controller
@RequestMapping(value="/webSocket")
public class WebSocketAction {
	
	private SimpMessagingTemplate template;

    @Autowired
    public WebSocketAction(SimpMessagingTemplate template) {
        this.template = template;
    }

    @RequestMapping(value="/greetings", method=RequestMethod.POST)
    public void greet(String greeting) {
    	Message msg = new Message();
    	msg.setContent("test");
    	msg.setFrom("testA");
    	msg.setId(0);
    	msg.setTo("testB");
    	msg.setType("MSG");
        String text = JSON.toJSONString(msg);
        this.template.convertAndSend("/topic/greetings", text);
    }
	   @RequestMapping(value="/textWeb")
	   public String textWeb(){
		   
		   return "webSocket/web_socket";
	   }
}
