package com.hy.tools.gen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.hy.tools.common.TemplatePath;
import com.hy.tools.uitl.FreemarkerUtil;
import com.hy.tools.uitl.StringUtil;
import com.hy.tools.uitl.TimeUtil;

/**
 * <pre>
 * 基础DAO生成类
 * </pre>
 * 
 * @author 黄云
 * 2015-6-11
 */
public class GenDao {

	public static String daoPath = "\\src\\${packagePath}\\dao\\";
	
	static boolean isoverwrite=true;
	
	/**
	 * 生成常用DAO结构
	 */
	public static void gen(){
		gen(isoverwrite);
	}
	
	/**
	 * 生成常用DAO结构
	 */
	public static void gen(boolean overwrite){
		isoverwrite = overwrite;
		try{
			String database = "${dbName}";
			String pkg = "${packageName}";
			pkg = pkg.substring(0,pkg.lastIndexOf("."));
			
			Connection conn = null;
			String url = "jdbc:mysql://localhost:3306/${dbName}?"
	                + "user=${user}&password=${pwd}&useUnicode=true&characterEncoding=UTF8";
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
	        // 一个Connection代表一个数据库连接
	        conn = DriverManager.getConnection(url);
	        
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("select table_name,table_comment,table_collation from information_schema.tables where table_schema=\""+database+"\"");
	        
	        while (rs.next()) {
	        	String tableName = StringUtil.upFirstChar(rs.getString("table_name"));
	        	String tableComment = rs.getString("table_comment");
				String tableName_R = tableName.toString();
				String tableName_s = tableName.toString().toLowerCase();
				
				String temp = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.dao);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("packageName", "${packageName}");
				data.put("package", pkg);
				data.put("project", database);
				data.put("tableName", tableName);
				if(tableName.equals("Admin")){
					data.put("isAdmin", true);
				}
				data.put("tableComment", tableComment);
				data.put("tableName_R", tableName_R);
				data.put("tableName_s", tableName_s);
				data.put("now", TimeUtil.getNowTime("yyyy-MM-dd"));
				String result = FreemarkerUtil.getTemplate(temp, data);
				if(isoverwrite){
					StringUtil.write(System.getProperty("user.dir")+daoPath+tableName_R+"Dao.java", result);
				}else{
					StringUtil.write(System.getProperty("user.dir")+"\\gen"
							+ "\\dao\\" + tableName_R+"Dao.java", result);
				}
			}
	        System.out.println("生成完毕！");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		GenDao.gen();
	}
	
}
