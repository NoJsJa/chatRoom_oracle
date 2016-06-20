package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.handleAction;
import bean.person;
import net.sf.json.JSONObject;
import dao.JdbcBean;

public class handleSingleAction extends HttpServlet {

	private static final long serialVersionUID = -2897324093051391345L;
	JdbcBean jdbcBean = new JdbcBean();

	public handleSingleAction() {
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
		String action = request.getParameter("action");
		if (action == null) {
			System.out.println("action not null!");
			return;
		}
		//判断是否注销
		if (request.getSession().getAttribute("name") == null) {
			
			System.out.println("handleSingleAction---session invalid");
			//向客户端回写
			out.print("error");
			return;
		}
		
		if (action.equals("updateFriend")) {
			
			System.out.println("updateFriend");
			updateFriend(request, response, out);
		}
		
		if (action.equals( "updateMessage")) {
			
			System.out.println("updateMessage");
			updateMessage(request, response, out);
		}
	
		if (action.equals("updateSession")) {
			
			System.out.println("updateSession");
			updateSession(request, response, out);
		}
		
		if (action.equals("closeSession")) {
			
			System.out.println("closeSession");
			closeSession(request, response, out);
		}
		
		if (action.equals("updateData")) {
			
			System.out.println("updateData");
			updateData(request, response, out);
		}
		
		if (action.equals("updateImg")) {
			
			System.out.println("updateImg");
			updateImg(out ,request);
		}
		
		if(action.equals("deleteFriend")){
			
			System.out.println("deleteFriend");
			deleteFriend(request);
		}
		
		if(action.equals("addFriend")) {
			
			System.out.println("addFriend");
			addFriend(request, out);
		}
		
		if(action.equals("queryUser")){
			
			System.out.println("queryUser");
			queryUser(request, out, response);
		}
		
		if (action.equals("readFile")) {
			
			System.out.println("readFile");
			readFile(out, request);
		}
		
		if (action.equals("fileUpload")) {
			
			System.out.println("fileUpload");
			fileUpload(out, request);
		}
		
	}
	
	//更新头像
		public void updateImg(PrintWriter out ,HttpServletRequest request) {
			
			String name = request.getSession().getAttribute("name").toString();
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String imgPath = "files\\users\\" + name.trim() + "\\";
			
			ServletContext servletContext = this.getServletConfig().getServletContext();
			handleAction handleAction = new bean.handleAction();
			
			if (handleAction.uploadFile(realPath, servletContext, request, imgPath, name)) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("imgUrl", "/chatRoom/files/users/" + name + "/" + name + ".jpg");
				System.out.println(jsonObject.toString());
				out.print(jsonObject.toString());
				return;
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", "imgUploadError");
			System.out.println(jsonObject.toString());
			out.print(jsonObject.toString());
		}
	
	//上传文件到服务器
	public void fileUpload(PrintWriter out, HttpServletRequest request) {
		
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String filePath = "files\\sharedFile\\";
		ServletContext servletContext = this.getServletConfig().getServletContext();
		handleAction handleAction = new handleAction();
		
		if(handleAction.uploadFile(realPath, servletContext, request, filePath, null)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("tips", "okay");
			System.out.println(jsonObject.toString());
			out.print(jsonObject.toString());
			return;
		}
	
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("tips", "uploadFail");
		System.out.println(jsonObject.toString());
		out.print(jsonObject.toString());
	}
	
	//读取共享文件列表
	public void readFile(PrintWriter out, HttpServletRequest request) {
		
		Map<String, String>fileListMap = new HashMap<String, String>();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		System.out.println(realPath);
		handleAction handleAction = new handleAction();
		handleAction.readSharedFileList(fileListMap,realPath);
		JSONObject jsonObject = JSONObject.fromObject(fileListMap);
		System.out.println(jsonObject.toString());
		
		out.print(jsonObject.toString());
	}
	
	//查询用户
	public void queryUser(HttpServletRequest request, PrintWriter out, HttpServletResponse response) {
		
		String name = request.getParameter("nameConditionInput");
		String sex = request.getParameter("sexSelect");
		String activity = request.getParameter("activitySelect");
		System.out.println(name + "|" + sex + "|" + activity +"|");
		//开始筛选的起始位置
		int index = 0;
		if (request.getParameter("index") != null) {
			index = Integer.parseInt(request.getParameter("index"));
		}
		
		//存储选择的用户
		List<person> personList = new ArrayList<person>();
	/*	JdbcBean jdbcBean = new JdbcBean();*/
		jdbcBean.getQueryResult(name, sex, activity, personList, index);
		request.setAttribute("personList", personList);
		System.out.println(personList);
		try {
			request.getRequestDispatcher("/setting.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//添加好友
	public void addFriend(HttpServletRequest request, PrintWriter out) {
		
		String friendName = request.getParameter("friend");
		String name = request.getSession().getAttribute("name").toString();
		if (friendName.equals(name)) {
			out.print("result_addSelf");
			return;
		}
		/*JdbcBean jdbcBean = new JdbcBean();*/
		String addResult =  jdbcBean.addFriend(name, friendName);
		if (addResult.equals("ok")) {
			out.print(friendName);
		}else{
			out.print(addResult);
		}
		
	}
	
	//删除好友
	public void deleteFriend(HttpServletRequest request) {
		
		String friendName = request.getParameter("friendName");
		String name = request.getSession().getAttribute("name").toString();
		jdbcBean.deleteFriend(name, friendName);
	}
	
	//更新资料
	public void updateData(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		String sex = request.getParameter("sex");
		String motto = request.getParameter("motto");
		String name = request.getSession().getAttribute("name").toString();
		
		String sqlString = "update chat_user set sex=" + "'" + sex + "'" + ", " + "motto=" + "'" + motto + "'" + " where name=" + "'" + name + "'";
		JdbcBean jdbcBean = new JdbcBean();
		try {
			jdbcBean.getConnection();
			jdbcBean.update(sqlString);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("sex", sex);
		dataMap.put("motto", motto);
		JSONObject jsonObject = JSONObject.fromObject(dataMap);
		System.out.println(jsonObject.toString());
		out.print(jsonObject.toString());
	}
	
	//更新好友操作
	public void updateFriend(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		/*JdbcBean jdbcBean = new JdbcBean();*/
		HttpSession session = request.getSession();
		//请求更新好友者姓名
		String name = session.getAttribute("name").toString();
		
		//存储所有好友的map
		Map<String, String> friendMap = new HashMap<String, String>();
		jdbcBean.getFriend(name, friendMap);
		JSONObject jsonObject = JSONObject.fromObject(friendMap);
		System.out.println(jsonObject.toString());
		//向客户端发回响应信息
		out.print(jsonObject.toString());
	}
	
	//更新消息操作
	public void updateMessage(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		/*JdbcBean jdbcBean = new JdbcBean();*/
		HttpSession session = request.getSession();
		String name = session.getAttribute("name").toString();
		int circle = 24;
		//服务器hold连接
		while (true) {
			//查询，默认hold住连接2分钟
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if (jdbcBean.hasMessage(name)) {
				Map<String, String> messageMap = new HashMap<String, String>();
				jdbcBean.getMessage(name, messageMap);
				JSONObject object = JSONObject.fromObject(messageMap);
				System.out.println(object.toString());
				out.print(object.toString());
				return;
			}
			
			if(--circle == 0) {
				System.out.println("no-message");
				out.print("noMessage");
				return;
			}
		}
	}
	
	//更新会话对象的个人信息
	public void updateSession(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		//发送消息的对象
		String name_sendTo = request.getParameter("name_sendTo");
		System.out.println("name_sendTo: " + name_sendTo);
		/*JdbcBean jdbcBean = new JdbcBean();*/
		HttpSession session = request.getSession();
		String name_sender = session.getAttribute("name").toString();
		
		Map<String, String> informationMap = new HashMap<String, String>();
		jdbcBean.getInformation(name_sendTo, informationMap);
		jdbcBean.setStatus(name_sender, name_sendTo, "online");
		//删除本条消息缓存
		jdbcBean.deleteMessageBuffer(name_sendTo, name_sender);
		
		JSONObject object = JSONObject.fromObject(informationMap);
		System.out.println(object.toString());
		
		out.print(object.toString());
	}
		
	//结束以前的消息会话
	public void closeSession( HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		String name_sendTo = request.getParameter("name_sendTo");
		HttpSession session = request.getSession();
		String name_sender = session.getAttribute("name").toString();
		
		/*JdbcBean jdbcBean = new JdbcBean();*/
		//关闭当前会话的
		jdbcBean.setStatus(name_sender, name_sendTo, "offline");
	}
	
	public void init() throws ServletException {
		// Put your code here
		
	}

}


















