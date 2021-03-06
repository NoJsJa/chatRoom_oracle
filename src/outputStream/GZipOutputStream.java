/*自定义的一个outputStream类，先将数据缓存起来，然后进行压缩，输出到客户端浏览器*/
package outputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

public class GZipOutputStream extends ServletOutputStream{

	private HttpServletResponse response;
	//JDK自带的压缩数据的类
	private GZIPOutputStream gzipOutputStream;
	//存放压缩数据
	private ByteArrayOutputStream byteArrayOutputStream;
	
	public GZipOutputStream(HttpServletResponse response) throws IOException{
		
		this.response = response;
		byteArrayOutputStream = new ByteArrayOutputStream();
		gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
	}
	@Override
	//输出到JDK的GZIP类中
	public void write(int b) throws IOException {
		
		gzipOutputStream.write(b);
	}
	
	//执行压缩并将数据输出到浏览器
	public void close() throws IOException{
		
		//执行压缩
		gzipOutputStream.finish();

		//将压缩后的数据输出到客户端
		byte[] content = byteArrayOutputStream.toByteArray();
		
		//设定压缩格式为gzip，客户端浏览器会将其解压
		response.addHeader("Content-Encoding", "gzip");
		response.addHeader("Content-Length", Integer.toString(content.length));
		
		ServletOutputStream out = response.getOutputStream();
		out.write(content);
		out.close();
	}

	public void flush()throws IOException {
		
		gzipOutputStream.flush();
	}
	
	//可以处理字符流，二进制流和字节流
	
	public void write(byte[] b, int off, int len)throws IOException {
		
		gzipOutputStream.write(b, off, len);
	}
	
	public void write(byte[] b)throws IOException {
		
		gzipOutputStream.write(b);
	}
	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setWriteListener(WriteListener arg0) {
		// TODO Auto-generated method stub
		
	}
}
