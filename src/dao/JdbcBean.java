package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


import bean.person;

public class JdbcBean { 

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
	
	//检查数据库中的账户和密码是否匹配
	public boolean hasUser(String name, String password) {
		
		getConnection();
		String sqlString = "select * from chat_user where name=" + "'" + name + "'";
		ResultSet rs = null;
		try {
			rs = query(sqlString);
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				if (rs.getString("password").equals(password)) {
					closeConnection(rs);
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return false;
	}
	
	//检查账号是否已经存在
	public boolean isExit(String name) {
		
		getConnection();
		String sqlString = "select * from chat_user where name=" + "'" + name + "'";
		ResultSet rs = null;
		try {
			rs = query(sqlString);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				/*closeConnection(rs);*/
				return true;
				
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		/*closeConnection(rs);*/
		return false;
		
	}
	
	//增加用户活跃度
	public void addActivity(String name) {
		
		getConnection();
		String sqlString = "update chat_user set activity=activity+1 where name=" + "'" + name + "'";
		try {
			update(sqlString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		closeConnection(null);
	}
	
	//获取朋友列表
	public void getFriend(String name, Map<String, String>friendMap) {
		
		getConnection();
		String sqlString = "select * from friend where friend=" + "'" + name + "'";
		ResultSet rs = null;
		try {
			rs = query(sqlString);
			while (rs.next()) {
				String friendName = rs.getString("name");
				String status = rs.getString("status");
				friendMap.put(friendName, status);		
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		closeConnection(rs);
	}
	
	public boolean hasMessage(String name_sendTo) {
		
		getConnection();
		String sqlsString = "select * from message where name_sendTo=" + "'" + name_sendTo + "'";
		ResultSet rs = null;
		try {
			rs = query(sqlsString);
			
			while (rs.next()) {
				closeConnection(rs);
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		closeConnection(rs);
		return false;
	}
	
	//获取服务器缓存消息
	public void getMessage(String name_sendTo, Map<String, String>messageMap) {
		
		String sqlString = "select * from message where name_sendTo=" + "'" + name_sendTo + "'";
		getConnection();
		ResultSet rs = null;
		try {
			rs = query(sqlString);
			while (rs.next()) {
				String name = rs.getString("name_sender");
				String message = rs.getString("msg");
				String imgIndex = rs.getString("imgIndex");
				if (imgIndex != null) {
					messageMap.put(name, imgIndex + ".png");
				}else {
					messageMap.put(name, message);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		closeConnection(rs);
		
	}
	
	//删除缓存消息
	public  void deleteMessageBuffer(String name_sender, String name_sendTo) {
		String deleteSql = "delete from message where name_sendTo=" + "'" +
				name_sendTo + "'" + " and " + "name_sender=" + "'" + name_sender + "'";
		getConnection();
		try {
			update(deleteSql);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		closeConnection(null);
	}
	
	//得到聊天对象的个人信息
	public void getInformation(String name_sendTo, Map<String, String>informationMap) {
		
		String sqlString = "select * from chat_user where name=" + "'" + name_sendTo + "'";
		getConnection();
		ResultSet rs = null;
		try {
			rs = query(sqlString);
			while (rs.next()) {
				//获取个人信息
				String name = rs.getString("name");
				String sex = "未公开";
				String motto = "未公开";
				if (rs.getString("sex") != null) {
					sex = rs.getString("sex");
				}
				if (rs.getString("motto") != null) {
					motto = rs.getString("motto");
				}
				int activity = Integer.parseInt(rs.getString("activity"));
				//用户评星
				String activityLevel = "一星级用户";
				if (activity >= 50) {
					activityLevel = "二星级用户";
					if (activity >= 100) {
						activityLevel = "三星级用户";
						if (activity >= 200) {
							activityLevel = "四星级用户";
							if (activity >= 500) {
								activityLevel = "五星级用户";
							}
						}
					}
				}
				
				informationMap.put("name", name);
				informationMap.put("sex", sex);
				informationMap.put("motto", motto);
				informationMap.put("activity", activityLevel);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		closeConnection(rs);
	}
	
	//设置点对点在线状态
	public void setStatus(String name_sender, String name_sendTo, String status) {
		
		getConnection();
		String sqlString;
		if (name_sendTo == null) {
			sqlString = "update friend set status=" + "'" + status + "'" + " where name=" + "'" + name_sender + "'";
		}else {
			sqlString = "update  friend set status=" +"'" + status + "'" + " where name=" + "'" + name_sender + "'" + " and " +
					"friend=" + "'" + name_sendTo + "'";
		}
		
		try {
			update(sqlString);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		closeConnection(null);
	}
	
	//得到聊天对象的在线状态
	public String getStatus(String name_sender, String name_sendTo) {
		
		//默认状态为会话离线
		getConnection();
		String status = "offline";
		String sqlString = "select * from friend where name=" +"'" + name_sendTo + "'" +" and " + "friend=" +"'" + name_sender + "'";
		ResultSet rs = null;
		
		try {
			rs = query(sqlString);
			while(rs.next()){
				status = rs.getString("status");
				//发回接收者在线信息
				return status;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		closeConnection(rs);
		return status;
	}
	
	public String addFriend(String name, String friendName) {
		
		getConnection();
		ResultSet rs = null;
		String result = "ok";
		String checkUseExit = "select * from chat_user where name=" + "'" +friendName + "'";
		String checkFriendStatus = "select * from friend where name=" + "'" +name+"'" + " and friend=" + "'" + friendName + "'";
		String sqlString = "insert into friend(id, name, friend, status) values(" + "friendSequence.NEXTVAL, " + "'" + name+"','" + friendName + "','" + "offline" + "')";
		String sqlString2 = "insert into friend(id, name, friend, status) values(" + "friendSequence.NEXTVAL, " +"'" + friendName+"','" + name + "','" + "offline" + "')";
		try {
			//检查用户是否已经是朋友
				rs = query(checkFriendStatus);
				while (rs.next()) {
					closeConnection(rs);
					result = "result_exit";
					return result;	
				}
			//检查是否存在要添加的用户
				rs = query(checkUseExit);
				while (!rs.next()) {
					closeConnection(rs);
					result = "result_noUser";
					return result;
				}
			
			stmt.executeUpdate(sqlString);
			stmt.executeUpdate(sqlString2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection(null);
		return result;
	}
	
	public void deleteFriend(String name, String friendName) {
		
		String sqlString = "delete from friend where name=" + "'" + name +"'" + " and " + "friend=" + "'" + friendName + "'";
		String sqlString2 = "delete from friend where name=" + "'" + friendName +"'" + " and " + "friend=" + "'" + name + "'";
		getConnection();
		try {
			stmt.executeUpdate(sqlString);
			stmt.executeUpdate(sqlString2);
			closeConnection(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//缓存一条消息
	public void setMessageBuffer(String name_sender, String name_sendTo, String msg, String imgIndex) {
		
		//删除以前缓存的消息
		String deleteSql = "delete from message where name_sendTo=" + "'" +
		name_sendTo + "'" + " and " + "name_sender=" + "'" + name_sender + "'";
		getConnection();
		try {
			update(deleteSql);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			closeConnection(null);
			System.out.println(e.getMessage());
		}
		
		//插入图片路径或是消息文字
		String sqlString = "insert into message(id, name_sendTo, name_sender, msg) values(" + "friendSequence.NEXTVAL, " + "'"+ name_sendTo + "','" + name_sender + "','" + msg + "')"; 
		if (imgIndex != null) {
			sqlString = "insert into message(id, name_sendTo, name_sender, imgIndex) values(" + "friendSequence.NEXTVAL, " +"'"+ name_sendTo + "','" + name_sender + "','" + imgIndex + "')";
		}else {
			sqlString = "insert into message(id, name_sendTo, name_sender, msg) values(" + "friendSequence.NEXTVAL, " + "'"+ name_sendTo + "','" + name_sender + "','" + msg + "')";
		}
		//重新缓存一条消息
		
		try {
			update(sqlString);
			closeConnection(null);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			closeConnection(null);
			System.out.println(e.getMessage());
		}
	}
	
	//得到用户查询结果
	public void getQueryResult(String name, String sex, String activity, List<person> personList, int index) {
		
		getConnection();
		ResultSet rs = null;
		//查询语句
		String sqlString = "Select * from chat_user";
		//筛选条件
		String whereCondition = "";
		//设置读取数目
		int readCount = 10;
		
		if (name != null && name != "") {
			whereCondition += " where name LIKE " +"'" +"%" + name + "%" +"'";
		}
		
		if (sex != null && !sex.equals("none")) {
			if (whereCondition.length() == 0) {
				whereCondition += " where sex=" + "'" +sex + "'";
			}else{
				whereCondition += " and sex=" + "'" + sex + "'";
			}
		}
		
		if (activity != null && !activity.equals("none")) {
			if (whereCondition.length() == 0) {
				whereCondition += " where activity >= " + "'" + activity + "'";
			}else{
				whereCondition += " and activity >= " + "'" + activity + "'";
			}
		}
		//筛选后的查询语句
		String sql = sqlString + whereCondition;
		System.out.println("sql: " + sql);
		try {
			rs = query(sql);
			while (rs.next()) {
				if (--index > 0) {
					continue;
				}
				person p = new person();
				p.setName(rs.getString("name"));
				p.setSex(rs.getString("sex"));
				p.setMotto(rs.getString("motto"));
				p.setActivity(rs.getString("activity"));
				/*p.setCreateTime(rs.getTimestamp("create_time"));*/
				personList.add(p);
				//读取10条就返回
				if (--readCount == 0) {
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection(rs);
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
