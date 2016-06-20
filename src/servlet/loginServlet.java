package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JdbcBean;

public class loginServlet extends HttpServlet {

	private static final long serialVersionUID = 3508229429641892404L; 

	public loginServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		JdbcBean jdbcBean = new JdbcBean();
		//注意中文字符的转换
		/*String name =  new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");*/
		String name = request.getParameter("name");
		System.out.println("中文名:" + name);
		/*String password =new String(request.getParameter("password").getBytes("iso-8859-1"),"utf-8");*/
		String password = request.getParameter("password");
		String action = request.getParameter("signUp");
		if (action != null) {	
			//执行查询注册的逻辑
			if (jdbcBean.isExit(name)) {
				out.print("no");
				return;
			}else {
				String sql = "insert into chat_user(name, password, activity) values(" + "'" + name + "', " + "'" + password + "', '" + 0 +"')";
				try {
					jdbcBean.update(sql);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				out.print("okay");
				return;
			}
			
		} else {
			//执行登录的逻辑
			if (jdbcBean.hasUser(name, password)) {
				jdbcBean.addActivity(name);
				request.getSession().setAttribute("name", name);
				request.getRequestDispatcher("/homePage.jsp").forward(request, response);
				
			}else {
				boolean loginError = true;
				request.setAttribute("loginError", loginError);
				request.getRequestDispatcher("/index.jsp?").forward(request, response);
			}
		}
		
	}

	
	public void init() throws ServletException {
		// Put your code here
	}

}
