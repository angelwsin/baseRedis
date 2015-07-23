import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.p2p.bean.user.User;
import com.p2p.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = false)
public class UserTest  {
	
	   @Resource
	   UserService userService;
	   @Test
	   public void test(){
		   User user = new User();
		   user.setUsername("zhangsan");
		   user.setPassword("123456");
		   userService.save(user);
	   }
	   
	  @Test
	   public void testfind(){
		   for(User user:userService.findUsers()){
			   System.out.println(user.getUsername()+":"+user.getPassword());
		   }
	   }
	  
	  
}
