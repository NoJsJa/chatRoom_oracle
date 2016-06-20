package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ApplicationConstants;

public class updateOnline extends HttpServlet {

	private static final long serialVersionUID = -4448772393958807726L;

	public updateOnline() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put you	r code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if (request.getSession().getAttribute("name") == null) {
			
			System.out.println("updateOnline---session invalid");
			//向客户端回写
			out.print("error");
			
			return;
		}
		//调用javabean
		List<String> onlineList = ApplicationConstants.getSessionMap();
		System.out.println(onlineList.toString());
		out.print(onlineList.toString());
	}


	public void init() throws ServletException {
		// Put your code here
	}

}
