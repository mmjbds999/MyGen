package ${packageName}.controller;

import java.io.File;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * BaseAction
 * </pre>
 * 
 * @author 黄云
 * 2015年12月7日
 */
public class BaseAction {

	private static Logger logger = Logger.getLogger(BaseAction.class);
	
	@Autowired
    protected HttpServletRequest request;
    
	/**
     * 文件上传--暂时不限制格式
     * @param file
     * @param response
     */
    public String fileUpload(MultipartFile file, HttpServletResponse response) {
    	String result = null;
        String filePath = null;
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                //获取项目路径
            	String tomcatPath = request.getSession().getServletContext().getRealPath("/");
                String uploadDir = tomcatPath+"upload";
                //创建文件夹
                File dirPath = new File(uploadDir);
                if (!dirPath.exists()) {
                    dirPath.mkdirs();
                }
                // 文件保存路径
                Date date = new Date();
                Random rd = new Random();
                String fileName = date.getTime() + rd.nextInt(100) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
                filePath = uploadDir + "/" + fileName;
                // 转存文件
                file.transferTo(new File(filePath));
                
                StringBuffer url = request.getRequestURL();  
                String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length())
                		.append(request.getContextPath()).append("/").toString();  
                result = tempContextUrl+"upload/"+fileName;
                
                logger.debug("------文件上传成功------");
            } catch (Exception e) {
            	logger.debug("------文件上传失败，妈蛋------");
                e.printStackTrace();
            }
        }
        return result;
    }
	
//    /**
//     * 获取user
//     * @return
//     */
//    public Admin getUser(){
//    	return (Admin)request.getSession().getAttribute("admin");
//    }
    
}
