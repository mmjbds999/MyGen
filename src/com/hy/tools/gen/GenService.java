package com.hy.tools.gen;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;

import com.hy.tools.common.TemplatePath;
import com.hy.tools.uitl.FreemarkerUtil;
import com.hy.tools.uitl.PackageUtil;
import com.hy.tools.uitl.StringUtil;
import com.hy.tools.uitl.TimeUtil;

/**
 * <pre>
 * 生成Service
 * </pre>
 * 
 * @author 黄云
 * 2015年12月14日
 */
public class GenService {

	public static String servicePath = "\\src\\${packagePath}\\service\\";
	
	public static String packagePath = "${packageName}";
	
	static boolean isoverwrite=true;
	
	/**
	 * 生成一堆Service
	 */
	public static void genServiceList(){
		genServiceList(isoverwrite);
	}
	
	/**
	 * 生成一堆Service
	 */
	public static void genServiceList(boolean overwrite){
		isoverwrite = overwrite;
		String path = "${packageName}.entity";
		try {
			List<String> clazzs = PackageUtil.getClassName(path);
			if(clazzs!=null&&clazzs.size()>0){
				for (String clazzName : clazzs) {
					Class<?> clazz = Class.forName(clazzName);
					genService(clazz);
				}
			}
			System.out.println("生成完成！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成一个service
	 * @param clazz
	 */
	public static void genService(Class<?> clazz){
		genService(clazz, isoverwrite);
	}
	
	/**
	 * 生成一个service
	 * @param clazz
	 */
	public static void genService(Class<?> clazz, boolean overwrite){
		isoverwrite = overwrite;
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.service);
		Map<String, Object> data = new HashMap<String, Object>();
		String className = StringUtil.lowFirstChar(clazz.getSimpleName());
		data.put("packageName", packagePath);
		data.put("className", className);
		data.put("classNameB", clazz.getSimpleName());
		data.put("nowDate", TimeUtil.getNowTime("yyyy-MM-dd"));
		
		Method[] methods = clazz.getMethods();
		List<String> sqlList = new ArrayList<String>();
		for (Method method : methods) {
			if(method.isAnnotationPresent(ManyToOne.class)){
				String joinmodel = method.getReturnType().getSimpleName();
				String sql = " left join "+className+"."+StringUtil.lowFirstChar(joinmodel)+" as "+StringUtil.lowFirstChar(joinmodel)+" ";
				sqlList.add(sql);
			}
		}
		data.put("sqlList", sqlList);
		
		String result = FreemarkerUtil.getTemplate(template, data);
		if(isoverwrite){
			StringUtil.write(System.getProperty("user.dir")+servicePath+clazz.getSimpleName()+"Service.java", result);
		}else{
			StringUtil.write(System.getProperty("user.dir")+"\\gen\\service\\"+clazz.getSimpleName()+"Service.java", result);
		}
	}
	
	/**
	 * MAIN--妈蛋
	 * @param args
	 */
	public static void main(String[] args) {
		genServiceList();
	}
	
}
