package responseWrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import outputStream.GZipOutputStream;

public class GZipResponseWrapper extends HttpServletResponseWrapper{

	private HttpServletResponse response;
	//自定义的outputstream，执行close的时候对数据进行压缩
	private GZipOutputStream gZipOutputStream;
	//自定义的writer，将内容输出到gZipOutputStream中
	private PrintWriter writer;
	
	//构造函数
	public GZipResponseWrapper(HttpServletResponse response) {
		super(response);
		this.response = response;
	}
	
	//处理二进制内容
	public ServletOutputStream getOutputStream()throws IOException {
		
		if (gZipOutputStream == null) {
			gZipOutputStream = new GZipOutputStream(response);
		}
		return gZipOutputStream;
		
	}

	//处理字符内容
	public PrintWriter getWriter() throws IOException{
		
		if (writer == null) {
			writer = new PrintWriter(new OutputStreamWriter(new GZipOutputStream(response), "UTF-8"));
		}
		return writer;
		
	}
	
	//压缩后数据长度会改变
	public void  setContentLength() {
		
	}
	
	public void  flushBuffer()throws IOException {
		
		gZipOutputStream.flush();
	}
	
	//将数据压缩，并输出到浏览器
	public void  finishResponse()throws IOException {
		
		if (gZipOutputStream != null) {
			//关闭流，压缩
			gZipOutputStream.close();
		}
		if (writer != null) {
			writer.close();
		}
	}
}
		
		
		
		
		
		
		
		