package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class characterEncodingFilter implements Filter{

	private String characterEncoding;
	private boolean enabled;
	
	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		characterEncoding = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO 自动生成的方法存根
		if (enabled && characterEncoding != null) {
			req.setCharacterEncoding(characterEncoding);
			res.setCharacterEncoding(characterEncoding);
		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO 自动生成的方法存根
		characterEncoding = config.getInitParameter("characterEncoding");
		enabled = "true".equalsIgnoreCase(config.getInitParameter("enabled").trim());
	}

}
