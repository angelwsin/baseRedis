package com.p2p.bean.msg;


public class Message {
	
	 private int id;
	 private String content;
	 private String  from;
	 private String to;
	 private String type;
	
	public int getId() {
		return id;
	}
	
	
	public String getFrom() {
		return from;
	}

	
	public void setFrom(String from) {
		this.from = from;
	}

	
	public String getTo() {
		return to;
	}

	
	public void setTo(String to) {
		this.to = to;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}
	
	 
	 
}
