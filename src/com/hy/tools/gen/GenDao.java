package com.hy.tools.gen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hy.tools.common.TemplatePath;
import com.hy.tools.uitl.FreemarkerUtil;
import com.hy.tools.uitl.StringUtil;
import com.hy.tools.uitl.TimeUtil;
import com.linzi.framework.utils.StringUtils;

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
	
	/**
	 * 生成常用DAO结构
	 */
	@SuppressWarnings("unchecked")
	public static void gen(){
		String database = "${dbName}";
		String pkg = "${packageName}";
		pkg = pkg.substring(0,pkg.lastIndexOf("."));
		Session session = GenEntityForHibernateConf.getSession();
		
		Query query = session.createSQLQuery("select table_name,table_comment,table_collation from information_schema.tables where table_schema=\""+database+"\"");
		List<Object[]> list = query.list();
		for (Object[] object : list) {
			System.out.println(object[0]);
			String tab = object[0].toString();
			StringBuffer tableName = new StringBuffer();
			if(tab.contains("_")){
				String[] tbs = tab.split("_");
				for (String s : tbs) {
					tableName.append(StringUtils.upperFirstChar(s));
				}
			}else{
				tableName.append(StringUtils.upperFirstChar(tab));
			}
			String tableComment = object[1].toString();
			String tableName_R = tableName.toString();
			String tableName_s = tableName.toString().toLowerCase();
			
			String temp = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.dao);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("packageName", "${packageName}");
			data.put("package", pkg);
			data.put("project", database);
			data.put("tableName", tableName);
			data.put("tableComment", tableComment);
			data.put("tableName_R", tableName_R);
			data.put("tableName_s", tableName_s);
			data.put("now", TimeUtil.getNowTime("yyyy-MM-dd"));
			String result = FreemarkerUtil.getTemplate(temp, data);
			StringUtil.write(System.getProperty("user.dir")+daoPath+tableName_R+"Dao.java", result);
		}
	}
	
	public static void main(String[] args){
		GenDao.gen();
	}
	
}
