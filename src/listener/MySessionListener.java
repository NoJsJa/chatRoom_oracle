package listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import dao.ApplicationConstants;
import dao.JdbcBean;

public class MySessionListener implements HttpSessionListener, HttpSessionAttributeListener{


	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {

		System.out.println("有用户访问网站！");
		ApplicationConstants.TOTAL_HISTORY_COUNT ++;		//总访问次数增加
		System.out.println(ApplicationConstants.TOTAL_HISTORY_COUNT);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		
		System.out.println("有用户从网站退出！");
	}

	//AttributeListener
	@Override
	public void attributeAdded(HttpSessionBindingEvent sessionBindingEvent) {
		
		// 属性增添--登录
		System.out.println("attributeAdded");
		if (sessionBindingEvent.getName().equals("name")) {
			String valueString = (String) sessionBindingEvent.getValue();
			HttpSession session = sessionBindingEvent.getSession();
			//将登录的用户放入在线用户列表
			ApplicationConstants.sesstion_map.put(session.getId(), session);
			
			 for (HttpSession sess : ApplicationConstants.sesstion_map.values()) {
				if (valueString.equals(sess.getAttribute("name"))  && sess.getId() != session.getId()) {
					System.out.println("已登录会话失效");
					//将以前的登录失效
					/*sess.invalidate(); 	*/
					sess.removeAttribute("name");
					/*ApplicationConstants.sesstion_map.remove(sess.getId());*/
					return;
				}
			 }
			 System.out.println("currentCount++");
			 ApplicationConstants.add_currentCount();
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent sessionBindingEvent) {
		
		// 属性移除--注销
		System.out.println("attributeRemoved");
		if (sessionBindingEvent.getName().equals("name")) {
			System.out.println("currentCount--");
			ApplicationConstants.sub_currentCount();		//在线人数减一
			JdbcBean jdbcBean = new JdbcBean();
			HttpSession session = sessionBindingEvent.getSession();
			String name = sessionBindingEvent.getValue().toString();
			//在线状态置为offline
			jdbcBean.setStatus(name, null, "offline");
			//从map中移除session
			ApplicationConstants.sesstion_map.remove(session.getId());
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent sessionBindingEvent) {
		
		// 属性修改--重新登录
		//包括刷新操作
		System.out.println("attributeReplaced");
		String nameString = sessionBindingEvent.getName();
		
		if (nameString.equals("name")) {
			for (HttpSession sess : ApplicationConstants.sesstion_map.values()) {
				if (sessionBindingEvent.getValue().equals(sess.getAttribute("name"))  && sessionBindingEvent.getSession().getId() != sess.getId()) {
					System.out.println("会话失效2");
					/*sess.invalidate();	*/
					sess.removeAttribute("name");
				}
			}
		}
	}
		

}
