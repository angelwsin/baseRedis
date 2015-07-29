package com.p2p.service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
/**
 * 
 * @package com.p2p.service
 * @author Ready
 * @date 2015年7月29日
 * @since 
 *spring  mq  接受消息
 */

@Service
public class ConsumerService {
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	 /**
     * 接受消息
     */
    public void receive(Destination destination) {
        TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
        try {
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
                    + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
