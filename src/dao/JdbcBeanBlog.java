package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.blog;
import bean.comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//读取博客数据的jdbcbean
public class JdbcBeanBlog {
	
	private String DBDriver = "oracle.jdbc.driver.OracleDriver";
	
	//云数据库
	private String DBUser = "Johnson";
	private String DBUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private String DBPassword = "yangwei020154";

	public Connection conn = null;
	public Statement stmt = null;
	
	public void getConnection() {
		
		try {
			Class.forName(DBDriver).newInstance();
			conn = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			stmt = conn.createStatement();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//执行查询操作
	public ResultSet query(String sql) throws Exception{
		
		ResultSet rs = stmt.executeQuery(sql);
		
		return rs;
	}
	
	//执行更新操作
	public void update(String sql) throws Exception{
		
		stmt.executeUpdate(sql);
	
	}
	
	//读取作者信息
	public void readAuthorData(JSONObject jsonObject, String author) {
		
		getConnection();
		String sqlString = "select * from chat_user where name=" + "'" + author + "'";
		ResultSet rs = null;
		try {
			rs = query(sqlString);
			while(rs.next()){
				String authorName = rs.getString("name");
				String authorSex = "未公开";
				String authorMotto = "未公开";
				if (rs.getString("sex") != null) {
					authorSex = rs.getString("sex");
				}
				if (rs.getString("motto") != null) {
					authorMotto = rs.getString("motto");
				}
				int activity = Integer.parseInt(rs.getString("activity"));
				//用户评星
				String authorActivity = "一星级用户";
				if (activity >= 50) {
					authorActivity = "二星级用户";
					if (activity >= 100) {
						authorActivity = "三星级用户";
						if (activity >= 200) {
							authorActivity = "四星级用户";
							if (activity >= 500) {
								authorActivity = "五星级用户";
							}
						}
					}
				}
				
				jsonObject.put("authorName", authorName);
				jsonObject.put("authorSex", authorSex);
				jsonObject.put("authorMotto", authorMotto);
				jsonObject.put("authorActivity", authorActivity);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		closeConnection(rs);
	}
	
	//查询某作者文章的所有标签
	public void readAllTags(String author, JSONArray jsonArray) {
		
		getConnection();
		String sqlString = "select DISTINCT tag from blog where author=" + "'" + author + "'";
		ResultSet rs = null;
		try {
			rs = query(sqlString);
			while(rs.next()){
				JSONObject jsonObject = new JSONObject();
				String tag = rs.getString("tag");
				jsonObject.put("tag", tag);
				jsonArray.add(jsonObject);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		closeConnection(rs);
	}
	
	
	//读取用户的所有评论
	public void readComment(JSONArray jsonArray, String title, String author) {
		
		getConnection();
		String sqlString = "select * from comments where title=" + "'" + title + "' and author=" + "'" + author + "'";
		ResultSet rs = null;
		try {
			rs = query(sqlString);
			while(rs.next()){
				JSONObject jsonObject = new JSONObject();
				String commentator = rs.getString("commentator");
				String content = rs.getString("content");
				String dates = rs.getDate("dates").toString();
				
				jsonObject.put("commentator", commentator);
				jsonObject.put("content", content);
				jsonObject.put("dates", dates);
				jsonArray.add(jsonObject);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		closeConnection(rs);
	}
	
	//发布一条评论
	public void newComment(comment newComment) {
		
		getConnection();
		String sqlString = "INSERT INTO comments(id,title,author,commentator,content,dates) values (" + 
				  "COMMENTSEQUENCE.NEXTVAL, " + 
				 "'" + newComment.getTitle() + 
				 "', '" + newComment.getAuthor() + 
				 "', '" + newComment.getCommentator() + 
				 "', '" + newComment.getContent() +
				 "', " + "to_date('" + newComment.getDates() + "', 'yyyy-mm-dd hh24:mi:ss'))";
		
		try {
			update(sqlString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		closeConnection(null);
	}
	
	//发布一则博客
	public void newBlogPost(blog newBlog) {
		
		getConnection();
		String sqlString = "insert into blog(id,title,abstract,author,content,dates,tag) values (" + 
				  "BLOGSEQUENCE.NEXTVAL, " + 
				 "'" + newBlog.getTitle() + 
				 "', '" + newBlog.getBlogAbstract() + 
				 "', '" + newBlog.getAuthor() + 
				 "', '" + newBlog.getContent() +
				 "', " + "to_date('" + newBlog.getDates() + "', 'yyyy-mm-dd hh24:mi:ss')," +
				 "'" + newBlog.getTag() + "')";
		
		try {
			update(sqlString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		closeConnection(null);
	}
	
	//删除一则博客
	public void deleteBlogPost(String title, String author){
		
		getConnection();
		String sqlString = "delete from blog where author=" + "'" + author + "' and " + "title=" + "'" + title + "'";
		try {
			update(sqlString);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		closeConnection(null);
	}
	
	
	//读取一个用户所有的文章，并写入JSONArray，也可以读取指定标签的文章
	public void readBlogList(JSONArray jsonArray, String author, String tag){
		
		/*得到数据库的连接*/
		getConnection();
		String queryString = "select * from blog where author=" + "'" + author + "'";
		if(tag != null){
			queryString = "select * from blog where author=" + "'" + author + "' and " + "tag=" + "'" + tag + "'";
		}
		
		ResultSet rs = null;
		try {
			rs = query(queryString);
			while(rs.next()){
				System.out.println("has next");
				JSONObject jsonObject = new JSONObject();
				String titleString = rs.getString("title");
				String authorString = rs.getString("author");
				String abstractString = rs.getString("abstract");
				String datesString = rs.getDate("dates").toString();
				String tagString = rs.getString("tag");
				
				jsonObject.put("title", titleString);
				jsonObject.put("author", authorString);
				jsonObject.put("abstract", abstractString);
				jsonObject.put("dates", datesString);
				jsonObject.put("tag", tagString);
				
				jsonArray.add(jsonObject);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection(rs);
	}
	
	//读取某一篇文章的所有内容
	public void readBlogPost(JSONObject jsonObject, String author, String title){
		
		getConnection();
		ResultSet rs = null;
		String queryString = "select * from blog where author=" + "'" + author + "' and" + " title=" + "'" + title + "'";
		JSONArray jsonArray = new JSONArray();
		
		try {
			rs = query(queryString);
			while (rs.next()) {
				String titleString = rs.getString("title");
				String authorString = rs.getString("author");
				String content = rs.getString("content");
				String dates = rs.getDate("dates").toString();
				String tagString = rs.getString("tag");
				
				getPostComments(jsonArray, authorString, titleString);
				jsonObject.put("title", titleString);
				jsonObject.put("author", authorString);
				jsonObject.put("content", content);
				jsonObject.put("dates", dates);
				jsonObject.put("tag", tagString);
				
				/*放入一个Json对象,json对象嵌套*/
				jsonObject.put("comments", jsonArray);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection(rs);
	}
	
	//得到谋篇文章的所有评论
	public void getPostComments(JSONArray jsonArray, String author, String title){
		
		String sqlString = "select * from comments where author=" + "'" + author + "' and " + "title=" + "'" + title + "'";
		try {
			Class.forName(DBDriver).newInstance();
			Connection Myconn = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
			Statement Mystmt = Myconn.createStatement();
			ResultSet rs = Mystmt.executeQuery(sqlString);
			
			while(rs.next()){
				JSONObject jsonObject = new JSONObject();
				String commentator = rs.getString("commentator");
				String content = rs.getString("content");
				String  dates = rs.getDate("dates").toString();
				jsonObject.put("commentator", commentator);
				jsonObject.put("dates", dates);
				jsonObject.put("content", content);
				
				jsonArray.add(jsonObject);
			}
			
			//关闭连接
			if (rs != null) {
				rs.close();
			}
			if (Mystmt != null) {
				Mystmt.close();
			}
			if (Myconn != null) {
				Myconn.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//关闭连接
	public void closeConnection(ResultSet rs) {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	}
	
}
