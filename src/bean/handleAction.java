package bean;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



public class handleAction {
	private String sharedFilePath = "files/sharedFile/";

	public String getSharedFilePath() {
		
		return sharedFilePath;
	}

	public void setSharedFilePath(String sharedFilePath) {
		
		this.sharedFilePath = sharedFilePath;
	}
	
	public void readSharedFileList(Map<String, String> map, String filePath) {
		
		File file = new File(filePath+getSharedFilePath());
		String[] fileList = file.list();
		for (int i = 0; i < fileList.length; i++) {
			map.put("file" + i, "/chatRoom/files/sharedFile/" + fileList[i]);
			System.out.println(fileList[i]);
		}
	}
	
	public boolean uploadFile(String realPath, ServletContext servletContext, HttpServletRequest request, String filePath, String name) {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");  
		System.out.println(servletContext.getAttribute("javax.servlet.context.tempdir").toString());
        factory.setRepository(repository);
        // Create a new file upload handler  
        ServletFileUpload upload = new ServletFileUpload(factory);  
     // Parse the request  
        try {  
            @SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(request);
            System.out.println(items.isEmpty());
            for(FileItem item : items) {  
                //其他参数 
            	System.out.println("1");
                String type = item.getContentType();  
                if(type == null) {  
                	//System.out.println(item.getString(item.getFieldName()));  
                    continue;  
                }  
                  
                //文件参数  
                String fileName = item.getName();
                if (name != null) {
					fileName = name + ".jpg";
				}
                //设置保存文件路径  
                String filepath = realPath + filePath;
                
                File dir = new File(filepath);
                if (!dir.exists()) {
					dir.mkdir();
				}
                File f = new File(dir, fileName);  
                  
                if(f.exists()) {
                    f.delete();  
                }  
                f.createNewFile();  
                  
                //保存  
                item.write(f);  
                  
            }  
        } catch (FileUploadException e) {  
            e.printStackTrace();
            return false;
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;
        }  
 
		return true;
	}
}
