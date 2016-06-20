package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.message;
import net.sf.json.JSONObject;
import dao.ApplicationConstants;
import dao.JdbcBean;

public class singleChat extends HttpServlet {

	private static final long serialVersionUID = -7982780991013787469L;

	public singleChat() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		//检测用户是否已经注销
		if (session.getAttribute("name") == null) {
			
			System.out.println("singleChat---session invalid");
			//向客户端回写
			out.print("error");
			
			return;
		}
		
		String name_sender = session.getAttribute("name").toString();
		//当前session中的用户
		String name = session.getAttribute("name").toString();
		System.out.println("userName: " + name);
		String name_sendTo = request.getParameter("name_sendTo");
		String message = request.getParameter("message");
		//发送的图片的索引号
		String imgIndex = request.getParameter("imgIndex");
		
		//发送消息
		if (message != null || imgIndex != null) {
			//创建消息对象
			JdbcBean jdbcBean = new JdbcBean();
			//查询消息接收者的状态
			String status =  jdbcBean.getStatus(name_sender, name_sendTo);
			if (status.equals("offline")) {
				//缓存一条消息到数据库
				if (message != null) {
					jdbcBean.setMessageBuffer(name_sender, name_sendTo, message, null);
				}else {
					if (imgIndex != null) {
						jdbcBean.setMessageBuffer(name_sender, name_sendTo, null, imgIndex);
					}
				}
				
			}
			else {
				//在线的话把消息添加到消息列表
				bean.message msg = new bean.message();
				//添加消息
				if (message != null) {
					msg.setMsg(message);
					msg.setImg(false);
				}else {
					if (imgIndex != null) {
						msg.setMsg(imgIndex);
						msg.setImg(true);
					}
				}
				
				msg.setSender(name_sender);
				msg.setSendTo(name_sendTo);
				msg.setSendTime(new Date());
				ApplicationConstants.singleMessageList.add(msg);
			}
			
			//存储消息的map
			Map<String, String> messageMap = new HashMap<String, String>();
			messageMap.put("name", name_sender);
			messageMap.put("message", message);
			messageMap.put("status", status);
			if (imgIndex != null) {
				messageMap.put("imgIndex", imgIndex);
			}
			
			JSONObject jsonObject = JSONObject.fromObject(messageMap);
			System.out.println("result1: " + jsonObject.toString());
			
			//向客户端写回responseText;
			out.print(jsonObject.toString());
			return;
		}
		
		System.out.println("reading List:");
		for (int i = 0; i < ApplicationConstants.singleMessageList.size(); i++) {
			System.out.println(ApplicationConstants.singleMessageList.get(i).getSender() + "  " + ApplicationConstants.singleMessageList.get(i).getMsg());
		}
		//客户端发出的服务器长连接请求
		if (request.getParameter("action") != null) {
			
			Map<String, String> messageMap = new HashMap<String, String>();
			//存储临时消息
			bean.message msg = new message();
			
			if (request.getParameter("action").equals("hold")) {
				//循环次数为120次，理想默认hold时间为2分钟
				int cycle = 240;
				boolean isBreak = false;
				while(true) {
					if (--cycle == 0) {
						out.print("noMessage");
						break;
					}
					//防止线程一直占用
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
					
					//遍历单人消息链表		
					Iterator<bean.message> iterator = ApplicationConstants.singleMessageList.iterator();
					while (iterator.hasNext()) {
						msg = iterator.next();
						if (msg.getSendTo().equals(name)) {
							messageMap.put("name", msg.getSender());
							if (msg.isImg()) {
								messageMap.put("imgIndex", msg.getMsg());
							}else {
								messageMap.put("message", msg.getMsg());
							}
							
							//移除当前消息节点
							iterator.remove();
							JSONObject jsonObject = JSONObject.fromObject(messageMap);
							System.out.println("iterator results:  t0 " + name + "  " + jsonObject.toString());
							out.print(jsonObject.toString());
							isBreak = true;
							System.out.println("break");
							break;
							
						}
					}
					//与客户端断开连接
					if (isBreak) {
						System.out.println("break2");
						break;
					}
				}
			}
		}
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
