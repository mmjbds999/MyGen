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
public class CreateMavenProject {

	public static String projectName = "Test";//项目名称--maven_artifactId
	
	public static String projectNameCN = "maven测试下";//项目名称--中文
	
	public static String dbName = "mybook";//数据库名称
	
	public static String packageName = "com.hy.test";//包名称--maven_groupId
	
	public static String packagePath = "com\\\\hy\\\\test";//包路径
	
	public static String path = "G:\\\\JavaSave2015\\\\";//需要创建项目的文件夹路径
	
	public static String user = "root";//数据库用户
	
	public static String pwd = "root";//数据库密码
	
	public static String maven_version = "1.0-SNAPSHOT";//版本号
	
	public static String maven_spring_version = "4.1.8.RELEASE";//spring版本号--默认用4.1.8.RELEASE
	
	/**
	 * 生成prop配置
	 */
	public static void genProp() {
		//log4j
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.log4j);//获取log4j模板文件str
		template = template.replace("${packageName}", packageName)
				.replace("${projectName}", projectName);
		StringUtil.write(path+projectName+"/src/main"+GenFilePath.prop+"log4j.properties", template);//写文件到项目路径
		
		//项目配置
		template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.project_prop);//获取项目配置路径
		template = template.replace("${packageName}", packageName)
				.replace("${projectName}", projectName)
				.replace("${dbName}", dbName)
				.replace("${user}", user)
				.replace("${pwd}", pwd)
				.replace("${projectNameL}", projectName.toLowerCase());
		StringUtil.write(path+projectName+"/src/main"+GenFilePath.prop+projectName.toLowerCase()+".properties", template);//写文件到项目路径
		System.out.println("prop配置生成完毕！");
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
		allCopy(System.getProperty("user.dir") + "/src/com/hy/tools", path+projectName+"/src/main/tools");
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
		StringUtil.write(path+projectName+"/src/main/resources/"+"all.xml", template);//写文件到项目路径
		
		//beanForDao.xml
		template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.beanForDao);
		template = template.replace("${packageName}", packageName);
		StringUtil.write(path+projectName+"/src/main/resources/"+"beanForDao.xml", template);//写文件到项目路径
		
		//beanForMVC.xml
		template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.beanForMVC);
		template = template.replace("${packageName}", packageName);
		StringUtil.write(path+projectName+"/src/main/resources/"+"beanForMVC.xml", template);//写文件到项目路径
		
		//beanForService.xml
		StringUtil.copyFile(System.getProperty("user.dir") + TemplatePath.beanForService,path+projectName+"/src/main/resources/"+"beanForService.xml");
		
		//client-webService.xml
		StringUtil.copyFile(System.getProperty("user.dir") + TemplatePath.webService,path+projectName+"/src/main/resources/"+"client-webService.xml");
		
		//dataAccess.xml
		StringUtil.copyFile(System.getProperty("user.dir") + TemplatePath.dataAccess,path+projectName+"/src/main/resources/"+"dataAccess.properties");
		System.out.println("conf配置生成完毕！");
	}
	
	/**
	 * 生成Web项目所需配置
	 */
	public static void genWebConfig(){
		//.setting
		allCopy(System.getProperty("user.dir")+"/template/maven/setting", path+projectName+"/.settings");
		
		//.project	.class
		allCopy(System.getProperty("user.dir")+"/template/maven/project", path+projectName);
		
		//web.xml
		allCopy(System.getProperty("user.dir")+"/template/maven/web", path+projectName+"/src/main/webapp/WEB-INF");
		
		System.out.println("WebConfig拷贝完毕！");
	}
	
	/**
	 * 生成BaseClass
	 */
	public static void genBaseClass(){
		//BaseAction.java
		String template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/BaseAction.java");
		template = template.replace("${packageName}", packageName);
		StringUtil.write(path+projectName+"/src/main/java/"+packagePath+"/controller/BaseAction.java", template);//写文件到项目路径
		
		//BaseServcie.java
		template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/BaseServcie.java");
		template = template.replace("${packageName}", packageName);
		StringUtil.write(path+projectName+"/src/main/java/"+packagePath+"/service/BaseServcie.java", template);//写文件到项目路径
		
		//BaseMainDao.java
		template = StringUtil.readFile(System.getProperty("user.dir")+"/template/base_class/BaseMainDao.java");
		template = template.replace("${packageName}", packageName);
		StringUtil.write(path+projectName+"/src/main/java/"+packagePath+"/dao/BaseMainDao.java", template);//写文件到项目路径
		
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
			CopyFiles.copyFolderWithSelf(System.getProperty("user.dir")+"/template/other/jsp", path+projectName+"/src/main/webapp");
			//META-INF
			CopyFiles.copyFolderWithSelf(System.getProperty("user.dir")+"/template/other/META-INF", path+projectName+"/src/main/webapp");
			//static
			CopyFiles.copyFolderWithSelf(System.getProperty("user.dir")+"/template/other/static", path+projectName+"/src/main/webapp");
			System.out.println("WebRoot拷贝完毕！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 拷贝jar包到生成的项目中--这里只拷贝一个，就是我们的工具包，只是为了提供给github用户使用的
	 */
	public static void copyLib(){
		try {
			CopyFiles.copyFolderWithSelf(System.getProperty("user.dir")+"/maven_lib", path+projectName);
			System.out.println("jar包拷贝完毕！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成maven的pom.xml
	 */
	public static void genPom(){
		String template = StringUtil.readFile(System.getProperty("user.dir") + "/template/maven/pom.xml");
		template = template
				.replace("${groupId}", packageName)
				.replace("${artifactId}", projectName)
				.replace("${version}", maven_version)
				.replace("${spring.version}", maven_spring_version);
		StringUtil.write(path+projectName+"/pom.xml", template);//写文件到项目路径
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
		genPom();
		genWebConfig();
		genTemplate();
		genTools();
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
		CreateMavenProject.genProject();
	}
	
}
