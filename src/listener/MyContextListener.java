package listener;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dao.ApplicationConstants;

public class MyContextListener  implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO 自动生成的方法存根
		System.out.println("服务器关闭了！");
		ApplicationConstants.START_DATE = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO 自动生成的方法存根
		System.out.println("服务器启动了！");
		ApplicationConstants.START_DATE = new Date(); //记录时间
	}

}
