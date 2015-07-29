package com.p2p.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultAction {
	
	
	  @RequestMapping(value="/index")
	  public void indexTo(){
		  
		  System.out.println("index");
	  }
	
	

}
