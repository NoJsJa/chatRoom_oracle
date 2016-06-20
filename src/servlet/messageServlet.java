//Servlet是单实例 的，注意实例变量的线程安全，每一个用户访问服务器的servlet都会新开一个线程
package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ApplicationConstants;
import net.sf.json.JSONObject;

public class messageServlet extends HttpServlet {
																					
	private static final long serialVersionUID = 221126869692535012L;

	public messageServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);		//转移请求
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); //发送Json信息
		
		if (request.getSession().getAttribute("name") == null) {
			
			//向客户端回写
			out.print("error");
			
			return;
		}
		String user = request.getParameter("name");
		String message = request.getParameter("message");
		String imgIndex = request.getParameter("imgIndex");
		int last_length = ApplicationConstants.message_length;//保存请求时的聊天状态
		
		//如果有消息
		if(message != null || (imgIndex != null)){

			synchronized (this) {
				//在返回参数中返回图片index或String消息
				ApplicationConstants.message_map.put("name", user);
				if (imgIndex != null) {
					String imgUrl = request.getContextPath() + "/img/emoji/" + imgIndex + ".png";
					System.out.println(imgUrl);
					ApplicationConstants.message_map.put("imgUrl", imgUrl);
				}else {
					ApplicationConstants.message_map.put("imgUrl", "");
				}
				if (message != null) {
					ApplicationConstants.message_map.put("message",message.trim());
				}else {
					ApplicationConstants.message_map.put("message", "");
				}
				
				//在返回参数中放入服务器信息
				if (ApplicationConstants.message_map.containsKey("current_count")) {
					ApplicationConstants.message_map.replace("current_count", Integer.toString(ApplicationConstants.CURRENT_LOGIN_COUNT));
				}else {
					ApplicationConstants.message_map.put("current_count", Integer.toString(ApplicationConstants.CURRENT_LOGIN_COUNT));
				}
				ApplicationConstants.message_length ++;
				
				return;
			}
				
		}
		
		while (true) {
			
			if (hasChange(request, last_length)) {
				
				JSONObject jsonObject = JSONObject.fromObject(ApplicationConstants.message_map);
				System.out.println(jsonObject.toString());		//控制台数据测试
				out.print(jsonObject.toString());	//向客户端返回json数据
				return;
			}
			//防止线程一直占用cpu
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
	}
	
	public boolean hasChange(HttpServletRequest request, int lastlength) {
		
		if (ApplicationConstants.message_length == lastlength) {
			return false;
		}
		
		return true;
	}
	
	public void init() throws ServletException {
			
	}
	

	
	
	
	
	
}
