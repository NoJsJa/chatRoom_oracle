package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class logoutServlet extends HttpServlet {

	private static final long serialVersionUID = 7838323896998553642L;

	public logoutServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		session.invalidate();
		if (request.getParameter("action") != null) {
			response.sendRedirect("/chatRoom/error.jsp");
		}else {
			response.sendRedirect("/chatRoom/index.jsp");
		}
		System.out.println("有用户注销了！");
	}

	public void init() throws ServletException {
		
	}

}
