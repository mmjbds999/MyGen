package com.hy.tools.main;

import java.io.File;

import com.hy.tools.common.GenFilePath;
import com.hy.tools.common.TemplatePath;
import com.hy.tools.uitl.CopyFiles;
import com.hy.tools.uitl.StringUtil;

/**
 * <pre>
 * 创建项目
 * </pre>
 * 
 * @author 黄云
 * 2015年12月17日
 */
public class CreateProject {

	public static String projectName = "Test";//项目名称
	
	public static String projectNameCN = "maven测试下";//项目名称--中文
	
	public static String dbName = "test";//数据库名称
	
	public static String packageName = "com.hy.test";//包名称
	
	public static String packagePath = "com\\\\hy\\\\test";//包路径
	
	public static String path = "G:\\\\JavaSave2015\\\\";//需要创建项目的文件夹路径
	
	public static String user = "root";//数据库用户
	
	public static String pwd = "root";//数据库密码
	
	
	//下面这些是发版的一些信息
	public static String copyright = "test";//版权所有
	
	public static String version = "V1.0";//版本号
	
	public static String author = "hy";//作者
	
	public static String webRoot = "WebRoot";//用eclipse的朋友请改成WebContent
	
	public static boolean hasAdmin = true;//是否有admin表，默认为true
	
	/**
	 * 生成prop配置
	 */
	public static void genProp() {
		//log4j
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.log4j);//获取log4j模板文件str
		template = template.replace("${packageName}", packageName)
				.replace("${projectName}", projectName);
		StringUtil.write(path+projectName+GenFilePath.prop+"log4j.properties", template);//写文件到项目路径
		
		//项目配置
		template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.project_prop);//获取项目配置路径
		template = template.replace("${packageName}", packageName)
				.replace("${projectName}", projectName)
				.replace("${dbName}", dbName)
				.replace("${user}", user)
				.replace("${pwd}", pwd)
				.replace("${projectNameL}", projectName.toLowerCase());
		StringUtil.write(path+projectName+GenFilePath.prop+projectName.toLowerCase()+".properties", template);//写文件到项目路径
		System.out.println("prop配置生成完毕！");
	}
	
	/**
	 * 生成Ant脚本
	 */
	public static void genAnt() {
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.build_prop);
		template = template.replace("##@projectName##", projectName)
				.replace("##@copyright##", copyright)
				.replace("##@version##", version)
				.replace("##@author##", author)
				.replace("##@webRoot##", webRoot);
		StringUtil.write(path+projectName+GenFilePath.build+"build.properties", template);//写文件到项目路径
		
		template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.build_xml);
		template = template.replace("##@projectName##", projectName)
				.replace("##@copyright##", copyright)
				.replace("##@version##", version)
				.replace("##@author##", author)
				.replace("##@webRoot##", webRoot);
		StringUtil.write(path+projectName+GenFilePath.build+"build.xml", template);//写文件到项目路径
		
		System.out.println("Ant脚本生成完毕！");
	}
	
	/**
	 * 拷贝模板
	 */
	public static void genTemplate(){
		try {
			CopyFiles.copyFolderWithSelf(System.getProperty("user.dir")+"/template", path+projectName);
			System.out.println("模板拷贝完毕！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成tools
	 */
	public static void genTools(){
		allCopy(System.getProperty("user.dir") + "/src/com/hy/tools", path+projectName+"/tools");
		System.out.println("tools拷贝完毕！");
	}
	
	/**
	 * 生成conf配置
	 */
	public static void genConf(){
		//all.xml
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.all);
		template = template.replace("${packageName}", packageName)
				.replace("${projectName}", projectName).replace("${projectNameL}", projectName.toLowerCase());
		StringUtil.write(path+projectName+GenFilePath.conf+"all.xml", template);//写文件到项目路径
		
		//beanForDao.xml
		template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.beanForDao);
		template = template.replace("${packageName}", packageName);
		StringUtil.write(path+projectName+GenFilePath.conf+"beanForDao.xml", template);//写文件到项目路径
		
		//beanForMVC.xml
		template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.beanForMVC);
		template = template.replace("${packageName}", packageName);
		StringUtil.write(path+projectName+GenFilePath.conf+"beanForMVC.xml", template);//写文件到项目路径
		
		//beanForService.xml
		StringUtil.copyFile(System.getProperty("user.dir") + TemplatePath.beanForService,path+projectName+GenFilePath.conf+"beanForService.xml");
		
		//client-webService.xml
		StringUtil.copyFile(System.getProperty("user.dir") + TemplatePath.webService,path+projectName+GenFilePath.conf+"client-webService.xml");
		
		//dataAccess.xml
		StringUtil.copyFile(System.getProperty("user.dir") + TemplatePath.dataAccess,path+projectName+GenFilePath.conf+"dataAccess.properties");
		System.out.println("conf配置生成完毕！");
	}
	
	/**
	 * 生成Web项目所需配置
	 */
	public static void genWebConfig(){
		//.setting
		allCopy(System.getProperty("user.dir")+"/template/setting", path+projectName+"/.settings");
		
		//.project	.class
		allCopy(System.getProperty("user.dir")+"/template/project", path+projectName);
		
		//web.xml
		allCopy(System.getProperty("user.dir")+"/template/web", path+projectName+"/WebRoot/WEB-INF");
		
		System.out.println("WebConfig拷贝完毕！");
	}
	
	/**
	 * 生成BaseClass
	 */
	public static void genBaseClass(){
		//BaseAction.java
		String template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/BaseAction.java");
		template = template.replace("${packageName}", packageName);
		if(hasAdmin){
			template = template.replace("###notes@@@", "");
			template = template.replace("###end@@@", "*/");
		}else{
			template = template.replace("###notes@@@", "*/");
			template = template.replace("###end@@@", "");
		}
		StringUtil.write(path+projectName+"/src/"+packagePath+"/controller/BaseAction.java", template);//写文件到项目路径
		
		//BaseServcie.java
		template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/BaseServcie.java");
		template = template.replace("${packageName}", packageName);
		StringUtil.write(path+projectName+"/src/"+packagePath+"/service/BaseServcie.java", template);//写文件到项目路径
		
		//BaseMainDao.java
		template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/BaseMainDao.java");
		template = template.replace("${packageName}", packageName);
		StringUtil.write(path+projectName+"/src/"+packagePath+"/dao/BaseMainDao.java", template);//写文件到项目路径
		
		System.out.println("BaseClass拷贝完毕！");
	}
	
	public static void genLogin(){
		//----------登录相关---------
		String template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/BaseJsonResponse.java");
		template = template.replace("##@packageName##", packageName);
		StringUtil.write(path+projectName+"/src/main/java/"+packagePath+"/service/json/BaseJsonResponse.java", template);//写文件到项目路径
		
		template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/JsonLoginResponse.java");
		template = template.replace("##@packageName##", packageName);
		StringUtil.write(path+projectName+"/src/main/java/"+packagePath+"/service/json/JsonLoginResponse.java", template);//写文件到项目路径
		
		template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/BaseLoginController.java");
		template = template.replace("##@packageName##", packageName);
		StringUtil.write(path+projectName+"/src/main/java/"+packagePath+"/controller/BaseLoginController.java", template);//写文件到项目路径
		
		template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/LoginAdminController.java");
		template = template.replace("##@packageName##", packageName);
		StringUtil.write(path+projectName+"/src/main/java/"+packagePath+"/controller/LoginAdminController.java", template);//写文件到项目路径
		
		template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/LonginService.java");
		template = template.replace("##@packageName##", packageName);
		StringUtil.write(path+projectName+"/src/main/java/"+packagePath+"/service/LonginService.java", template);//写文件到项目路径
		
		//------------------拦截器------------------
		template = StringUtil.readFile(System.getProperty("user.dir")+"/template/interceptor/LoginInterceptor.java");
		template = template.replace("##@packageName##", packageName);
		StringUtil.write(path+projectName+"/src/main/java/"+packagePath+"/interceptor/LoginInterceptor.java", template);//写文件到项目路径
	}
	
	/**
	 * 生成WebRoot内容
	 */
	public static void genWebRoot(){
		try {
			//jsp
			CopyFiles.copyFolderWithSelf(System.getProperty("user.dir")+"/template/other/jsp", path+projectName+"/WebRoot");
			//META-INF
			CopyFiles.copyFolderWithSelf(System.getProperty("user.dir")+"/template/other/META-INF", path+projectName+"/WebRoot");
			//static
			CopyFiles.copyFolderWithSelf(System.getProperty("user.dir")+"/template/other/static", path+projectName+"/WebRoot");
			System.out.println("WebRoot拷贝完毕！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 拷贝jar包到生成的项目中
	 */
	public static void copyLib(){
		try {
			CopyFiles.copyFolderWithSelf(System.getProperty("user.dir")+"/lib", path+projectName+"/WebRoot/WEB-INF");
			System.out.println("jar包拷贝完毕！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 整个文件夹的文件替换以及拷贝
	 * @param path
	 */
	private static void allCopy(String source, String to){   
        File file = new File(source);   
        File[] array = file.listFiles();   
          
        for(int i=0;i<array.length;i++){   
            if(array[i].isFile()){   
                String fp = array[i].getPath();
                String template = StringUtil.readFile(fp);
        		template = template.replace("${packageName}", packageName)
        				.replace("${projectName}", projectName)
        				.replace("${projectNameCN}", projectNameCN)
        				.replace("${path}", path)
        				.replace("${user}", user)
        				.replace("${pwd}", pwd)
        				.replace("${packagePath}", packagePath)
        				.replace("${packagePathShort}", packagePath.replace("com\\\\", ""))
        				.replace("${dbName}", dbName)
        				.replace("##@", "${")
        				.replace("##", "}");
        		StringUtil.write(to +"/"+ array[i].getName(), template);//写文件到项目路径
            }else if(array[i].isDirectory()){   
            	String dir = "\\com\\hy\\tools\\"+array[i].getPath().substring(array[i].getPath().lastIndexOf("\\"));
            	allCopy(array[i].getPath(), to+dir);   
            }
        }   
    }
	
	/**
	 * 创建正常web工程
	 */
	public static void genProject(){
		genProp();
		genConf();
		copyLib();
		genWebRoot();
		genBaseClass();
		genWebConfig();
		genTemplate();
		genTools();
		genAnt();
	}
	
	public static void main(String[] args) {
//		try {
//			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//			
//			//选择项目类型
//			System.out.print("请选择项目类型：1.普通MVC；2.基于spring--boot的Maven项目");
//			if(br.readLine().toLowerCase().equals("1")){
//				//项目名
//				System.out.print("请输入项目名（例如\"MySpring\"）：");
//		        String projectName = br.readLine();
//		        CreateProject.projectName = projectName;
//		        System.out.println("项目名："+projectName);
//		        
//		        //项目中文名
//		        System.out.print("请输入项目中文名（例如\"我的Spring\"）：");
//		        String projectNameCN = br.readLine();
//		        CreateProject.projectNameCN = projectNameCN;
//		        System.out.println("项目名CN："+projectName);
//		        
//		        //数据库名
//		        System.out.print("请输入数据库名（例如\"mydb\"）：");
//		        String dbName = br.readLine();
//		        CreateProject.dbName = dbName;
//		        System.out.println("数据库名："+dbName);
//		        System.out.print("是否更改数据连接用户名密码（默认：root，root）Y/N：");
//		        if(br.readLine().toLowerCase().equals("n")){
//		        	//用户
//		        	System.out.print("请输入用户名：");
//			        String user = br.readLine();
//			        CreateProject.user = user;
//			        System.out.println("用户名："+user);
//			        
//			        //密码
//			        System.out.print("请输入密码：");
//			        String pwd = br.readLine();
//			        CreateProject.pwd = pwd;
//			        System.out.println("密码："+pwd);
//		        }
//				
//		        //包名及包路径
//				System.out.print("请输入工程的包路径（例如\"com.hy.myspring\"）：");
//		        String pgName = br.readLine();
//		        CreateProject.packageName = pgName;
//		        CreateProject.packagePath = pgName.replace(".", "\\");
//		        System.out.println("包名："+pgName);
//		        
//		        //需要创建项目的文件夹路径
//		        System.out.print("请输入需要创建项目的文件夹路径（例如\"G:\\JavaSave2015\\\"）：");
//		        String path = br.readLine();
//		        CreateProject.path = path;
//		        System.out.println("文件夹路径："+pgName);
//		        
//		        //开始创建
//		        CreateProject.genProject();
//			}else{
//				System.out.println("还没写呢!");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		//上面的是打成jar包需要手动输入时用的
		CreateProject.genProject();
	}
	
}
