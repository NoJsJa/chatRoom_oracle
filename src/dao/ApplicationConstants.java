package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import bean.message;

public class ApplicationConstants {
	
	public static Map<String, HttpSession> sesstion_map = new HashMap<String, HttpSession>();			//	保存session信息的map键值对
	public static Map<String, String>message_map = new HashMap<String, String>();		//保存系统临时聊天信息的map键值对
	public static ArrayList<message> singleMessageList = new ArrayList<message>();		//存储系统单人聊天临时消息的ArrayList
	
	public static int message_length = 0;		//保存聊天信息的长度值

	public static int CURRENT_LOGIN_COUNT = 0;		//目前在线人数
	public static Date START_DATE = new Date();		//服务器启动时间
	public static int TOTAL_HISTORY_COUNT = 0;	//网站总访问次数
	
	public static void sub_currentCount() {
		
		if (CURRENT_LOGIN_COUNT > 0) {
			CURRENT_LOGIN_COUNT --;
		}else {
			CURRENT_LOGIN_COUNT = 0;
		}
	}
	
	public static void add_currentCount() {
		
		CURRENT_LOGIN_COUNT++;
	}
	
	public static List<String> getSessionMap () {
		
		//将所有在线用户名存储到链表返回String_name
		List<String> onlineList = new ArrayList<String>();
		try {
			for ( HttpSession session : ApplicationConstants.sesstion_map.values()) {
				onlineList.add(session.getAttribute("name").toString());
			} 
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return onlineList;
	}
	
}
