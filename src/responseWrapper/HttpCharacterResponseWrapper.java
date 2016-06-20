package responseWrapper;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

//自定义具备缓存功能的response
public class HttpCharacterResponseWrapper extends HttpServletResponseWrapper{

	//定义字符数据writer
	private CharArrayWriter charArrayWriter = new CharArrayWriter();
	public HttpCharacterResponseWrapper(HttpServletResponse response) {
		super(response);
		
	}

	public PrintWriter getWriter() {
		
		//重写返回PrintWriter对象的函数，覆盖父类方法
		return new PrintWriter(charArrayWriter);
		
	}
	
	public CharArrayWriter getCharArrayWriter() {
		
		//getter
		return charArrayWriter;
	}
}
