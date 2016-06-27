package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.blog;
import bean.comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import dao.JdbcBeanBlog;

public class blogServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public blogServlet(){
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
		
		/*获取动作参数*/
		String action = request.getParameter("action");
		
		if(action.equals("readList")){
			//读取博客列表
			readBlogList(request, response, out);
			
		}else if (action.equals("readPost")){
			//读取一则博客内容
			readBlogPost(request, response, out);
			
		}else if (action.equals("newPost")){
			//发布一则博客
			newBlogPost(request, response, out);
			
		}else if (action.equals("readTagPost")){
			//读取指定标签的博客
			readTagPost(request, response, out);
			
		}else if (action.equals("deletePost")){
			//删除一则博客
			deleteBlogPost(request, response, out);
			
		}else if (action.equals("newComment")){
			//指定用户添加留言信息
			newComment(request,response,out);
		}else if(action.equals("readComment")){
			//读取指定用户的留言信息
			readComment(request, response, out);
		}else if (action.equals("newBlogComment")){
			//发布指定文章的留言信息
			newBlogComment(request, response, out);
		}else if (action.equals("readAllTags")){
			//读取所有文章的标签
			readAllTags(request, response, out);
		}else if (action.equals("readAuthorData")){
			//读取用户信息
			readAuthorData(request, response, out);
		}
		
	}
	
	//读取用户信息
	public void readAuthorData(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		System.out.println("readAuthorData");
		String author = request.getParameter("author");
		JSONObject jsonObject = new JSONObject();
		
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		jdbcBeanBlog.readAuthorData(jsonObject, author);
		out.print(jsonObject.toString());
	}
	
	//读取所有文章的标签
	public void readAllTags(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		System.out.println("readAllTags");
		String author = request.getParameter("author");
		JSONArray jsonArray = new JSONArray();
		
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		jdbcBeanBlog.readAllTags(author, jsonArray);
		
		out.print(jsonArray.toString());
	}
	
	//发布指定文章的留言信息
	public void newBlogComment(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		System.out.println("newBlogComment");
		String author = request.getParameter("author");
		String commentator = request.getSession().getAttribute("name").toString();
		String content =request.getParameter("content");
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String dates = df.format(date);
		String title = request.getParameter("title");
		
		comment newComment = new comment();
		newComment.setAuthor(author);
		newComment.setCommentator(commentator);
		newComment.setContent(content);
		newComment.setDates(dates);
		newComment.setTitle(title);
		
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		jdbcBeanBlog.newComment(newComment);
		out.println("publish blog comment done.");
	}
	
	//读取指定用户的留言信息
	public void readComment(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		System.out.println("readComment");
		String author = request.getParameter("author");
		String title = "NULL";
		JSONArray jsonArray = new JSONArray();
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		jdbcBeanBlog.readComment(jsonArray, title, author);
		
		out.print(jsonArray.toString());
	}
	
	
	//指定用户添加留言信息
	public void newComment(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		System.out.println("newComment");
		String author = request.getParameter("author");
		//当前用户
		String commentator = request.getSession().getAttribute("name").toString();
		String content = request.getParameter("content");
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String dates = df.format(date);
		String title = "NULL";
		
		//创建评论bean
		comment newComment = new comment();
		newComment.setAuthor(author);
		newComment.setCommentator(commentator);
		newComment.setContent(content);
		newComment.setDates(dates);
		newComment.setTitle(title);
		
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		jdbcBeanBlog.newComment(newComment);
		out.print("publish comment done.");
		
	}
	
	//删除指定的博客
	public void deleteBlogPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
	
		System.out.println("deleteBlogPost");
		String author = request.getParameter("blogAuthor");
		String title = request.getParameter("blogTitle");
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		
		jdbcBeanBlog.deleteBlogPost(title, author);
		
		out.print("delete done.");
		
	}
	
	//读取指定标签的博客
	public void readTagPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		System.out.println("readTagPost");
		String author = request.getParameter("author");
		String tag = request.getParameter("tag");
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		JSONArray jsonArray = new JSONArray();
		
		jdbcBeanBlog.readBlogList(jsonArray, author, tag);
		
		if(jsonArray.isEmpty()){
			out.print("null");
			return;
		}
		
		out.print(jsonArray.toString());
	
	}
	
	//发布一则博客
	public void newBlogPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		System.out.println("newBlogPost");
		
		String author = request.getParameter("author");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String tag = request.getParameter("tag");
		//格式化日期
		Date date = new Date();
		DateFormat df = DateFormat.getDateTimeInstance();
		String dates = df.format(date);
		 
		String blogAbstract = request.getParameter("abstract");
		
		blog newBlog = new blog();
		newBlog.setAuthor(author);
		newBlog.setBlogAbstract(blogAbstract);
		newBlog.setContent(content);
		newBlog.setDates(dates);
		newBlog.setTag(tag);
		newBlog.setTitle(title);
		
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		jdbcBeanBlog.newBlogPost(newBlog);
		out.print("publish ok.");
		
	}
	
	//读取博客列表
	public void readBlogList(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		
		System.out.println("readBlogList");
		String author = request.getParameter("blogAuthor");
		System.out.println(author);
		/*所有的文章信息将被读入这个Json数组*/
		JSONArray jsonArray = new JSONArray();
		
		if(author == null){
			out.print("");
			return;
		}
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		jdbcBeanBlog.readBlogList(jsonArray, author, null);
		System.out.println("blogList: " + jsonArray.toString());
		if(jsonArray.isEmpty()){
			out.print("null");
			return;
		}
		
		out.print(jsonArray.toString());
		
	}
	
	//读取一篇博客的内容
	public void readBlogPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		
		System.out.println("readBLogPost");
		String author = request.getParameter("blogAuthor");
		String title = request.getParameter("blogTitle");
		
		/*将文章内容存入这个Json对象*/
		JSONObject jsonObject  = new JSONObject();
		
		JdbcBeanBlog jdbcBeanBlog = new JdbcBeanBlog();
		jdbcBeanBlog.readBlogPost(jsonObject, author, title);
		
		out.println(jsonObject.toString());
		
	}
	
	public void init() throws ServletException {
		// Put your code here
	}
	
}
