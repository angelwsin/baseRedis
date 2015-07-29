import javax.annotation.Resource;
import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.p2p.service.ConsumerService;
import com.p2p.service.ProductService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = false)
public class MqServiceTest {
	
	@Resource
	ProductService productService;
	@Resource
	ConsumerService consumerService;
	/**
     * 队列名queue1
     */
    @Autowired
    private Destination queueDestination;
	
	@Test
	public void sendTest(){
		
		// productService.sendMessage(queueDestination,"hello world!");
		 //consumerService.receive(queueDestination);
	}
	
	
}
