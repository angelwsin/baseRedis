package com.p2p.webService.client;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;


public class CallBackWs {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		//使用RPC方式调用WebService 
		  RPCServiceClient serviceClient = null;
		  try {
		         serviceClient = new RPCServiceClient();
		         Options options = serviceClient.getOptions();
		         //指定调用WebService的URL
		         EndpointReference targetEPR = new EndpointReference("http://localhost/axis2/services/HelloServiceTest");
		         options.setTo(targetEPR);
		         //指定sayHello方法的参数值
		         Object[] opAddEntryArgs = new Object[] {"于士博"};
		         //指定sayHello方法返回值的数据类型的Class对象
		         Class[] classes = new Class[] {String.class};
		         //指定要调用的sayHello方法及WSDL文件的命名空间
		         QName opAddEntry = new QName("http://webService.p2p.com", "sayBye");
		         //调用sayHello方法并输出该方法的返回值
		         String result = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)
		                         [0].toString();
		         serviceClient.cleanupTransport();  //为了防止连接超时
		         System.out.println(result);
		         //System.out.println(serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)   [0]);
		  } catch (AxisFault e) {
		   e.printStackTrace();
		  }
	}
}
