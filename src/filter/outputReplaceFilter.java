package filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ApplicationConstants;
import responseWrapper.HttpCharacterResponseWrapper;

public class outputReplaceFilter implements Filter{

	//载入配置文件
	private Properties properties = new Properties();
			
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		
		HttpServletRequest httpReq = (HttpServletRequest) req;
		httpReq.setCharacterEncoding("utf-8");
		HttpSession httpSession = httpReq.getSession(false);
		System.out.println(ApplicationConstants.message_map.get("name"));
		System.out.println(httpSession.getAttribute("name"));
		if (httpSession.getAttribute("name") != null && ApplicationConstants.message_map.get("name") != null) {
			System.out.println("equal:" + ApplicationConstants.message_map.get("name").equals(httpSession.getAttribute("name")));
		}
		System.out.println();
		HttpCharacterResponseWrapper responseWrapper = new HttpCharacterResponseWrapper((HttpServletResponse) res);
		chain.doFilter(req, responseWrapper);
	
		
		//得到response的输出内容
		String output = responseWrapper.getCharArrayWriter().toString();
		//非法标记	
		for(Object object : properties.keySet()){
			String key = (String)object;
			if (output.indexOf(key.trim()) != -1) {
				if (ApplicationConstants.message_map.get("name").equals(httpSession.getAttribute("name"))) {
					httpSession.setAttribute("name", null);
					return;
				}
				output = output.replace(key, properties.getProperty(key));
			}
		}
		
		PrintWriter out = res.getWriter();
		out.print(output);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
		String file = config.getInitParameter("file");
		String fileRealPath = config.getServletContext().getRealPath(file);
		
		try {
			//载入配置文件
			FileInputStream FS = new FileInputStream(fileRealPath);
			properties.load(FS);
			FS.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
