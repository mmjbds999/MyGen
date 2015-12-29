package com.hy.tools.gen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.GenerationType;

import com.hy.tools.common.SQLHelper;
import com.hy.tools.common.TemplatePath;
import com.hy.tools.object.Column;
import com.hy.tools.object.Table;
import com.hy.tools.uitl.StringUtil;

/**
 * 实体生成工具类
 * @author 云
 *
 */
public class MavenGenEntityForHibernateConf {
	
	/** 需要操作的table对象 */
	static Map<String, Table> tables;
	
	static boolean isoverwrite=true;
	
	/**
	 * 数据库类型枚举
	 * @author 云
	 *
	 */
	enum DBType{
		mysql,
		oracle
	}
	
	/** 数据库类型 */
	static DBType dbType=DBType.mysql;
	
	/** 数据库名称 */
	static String dbName="${dbName}";
	
	/** ID生成策略 */
	static Object generated=GenerationType.IDENTITY;
	
	/** 工程包名 */
	static String schemaname="${packagePathShort}";
	
	public static DBType getDbType() {
		return dbType;
	}

	public static void setDbType(DBType dbType) {
		MavenGenEntityForHibernateConf.dbType = dbType;
	}

	public static String getDbName() {
		return dbName;
	}

	public static void setDbName(String dbName) {
		MavenGenEntityForHibernateConf.dbName = dbName;
	}

	public static Object getGenerated() {
		return generated;
	}

	public static void setGenerated(Object generated) {
		MavenGenEntityForHibernateConf.generated = generated;
	}

	public static String getSchemaname() {
		return schemaname;
	}

	public static void setSchemaname(String schemaname) {
		MavenGenEntityForHibernateConf.schemaname = schemaname;
	}
	
	/**
     * 根据数据库名生成所有实体
     * @author 云
     */
	public static void genAll(){
		genAll(isoverwrite);
	}
	
    /**
     * 根据数据库名生成所有实体
     * @author 云
     */
	public static void genAll(boolean overwrite){
		isoverwrite = overwrite;
		if(tables==null)
			setTable();
		for (String key : tables.keySet()) {
			Table tab = tables.get(key);
			//循环执行单实体生成方法
			genEnt(tab.getName());
		}
		System.out.println("all-生成完成！");
	}
	
	/**
	 * 根据库名及表名生成单个实体
	 * @author 云
	 * @param tabName 表名
	 */
	public static void genEnt(String tabName){
		genEnt(tabName, isoverwrite);
	}
	
	/**
	 * 根据库名及表名生成单个实体
	 * @author 云
	 * @param tabName 表名
	 */
	public static void genEnt(String tabName, boolean overwrite){
		isoverwrite = overwrite;
		if(tables==null)
			setTable();
		if(tabName!=null&&!"".equals(tabName)&&!"null".equals(tabName)){
			Table table = tables.get(tabName);
			//赋值表对象
			String entityTemp = StringUtil.readFile(System.getProperty("user.dir")+TemplatePath.entity);
			if(StringUtil.isNotEmpty(entityTemp)){
				String other_import = "";
				//替换表信息
				entityTemp = entityTemp
						.replace("<entity_notes>", table.getNotes())
						.replace("<database>", dbName)
						.replace("<table_r>", table.getName())
						.replace("<table>", StringUtil.upFirstChar(table.getName()));
				
				//替换字段信息
				List<String> colTemps = StringUtil.getMarkStringList(entityTemp, "<COL>", "</COL>");
				if(colTemps!=null&&colTemps.size()>0){
					
					String colFieldTemp = colTemps.get(0);//字段模板
					String colAttrTemp = colTemps.get(1);//字段属性模板
					
					StringBuffer colField = new StringBuffer();//字段
					StringBuffer colAttr = new StringBuffer();//字段属性
					
					if(dbType.equals(DBType.mysql)){
						//MYSQL解析
						if(table.getColumns()!=null&&table.getColumns().size()>0){
							for (Column column : table.getColumns()) {
								
								String idFieldTemp = StringUtil.getMarkString(colAttrTemp, "<COL_ID>", "</COL_ID>");
								String idField = "";
								if(column.getKey().equals("PRI")){
									StringBuffer generator = new StringBuffer();//ID生成策略
									//根据类型替换ID生成策略
									if(generated instanceof GenerationType){
										generator.append("@GeneratedValue(strategy=GenerationType.")
										.append(generated.toString())
										.append(")");
									}else if(generated instanceof String){
										if(generated.equals("uuid")){
											generator.append("@GeneratedValue(generator = \"system-uuid\")");
											generator.append("\r\n\t");
											generator.append("@GenericGenerator(name = \"system-uuid\", strategy = \"uuid\")");
										}
									}
									idField = idFieldTemp.replace("<generator>", generator);
								}
								String type = column.getType();
								String rtype = "";//数据库字段类型
								String length = "";//数据库字段长度
								String etype = "";//JAVA字段类型
								String fmt = "";//JAVA字段类型
								StringBuffer column_attr = new StringBuffer();
								if(type.contains("(")){
									rtype = type.substring(0,type.indexOf("("));
									length = type.substring(type.indexOf("(")+1,type.indexOf(")"));
								}else{
									rtype = type;
								}
								
								if(rtype.equals("decimal")){
									if(column_attr.length()>0)
										column_attr.append(", ");
									etype = "Double";
									length = "";
									column_attr.append("precision=").append(column.getPrecision());
									column_attr.append(", scale=").append(column.getScale());
								}else if(rtype.equals("timestamp")){
									etype = "Timestamp";
									if(!other_import.contains("java.sql.Timestamp")){
										other_import += "import java.sql.Timestamp;\r\n";
									}
								}else if(rtype.equals("double")){
									etype = "Double";
								}else if(rtype.equals("float")){
									if(column_attr.length()>0)
										column_attr.append(", ");
									etype = "Float";
									length = "";
									column_attr.append("precision=").append(column.getPrecision());
									column_attr.append(", scale=").append(column.getScale());
								}else if(rtype.equals("varchar")){
									etype = "String";
								}else if(rtype.equals("char")){
									etype = "String";
								}else if(rtype.equals("text")){
									etype = "String";
									length = "254";
								}else if(rtype.equals("int")){
									etype = "Integer";
								}else if(rtype.equals("datetime")||rtype.equals("date")){
									etype = "Date";
									if(!other_import.contains("java.util.Date")){
										other_import += "import java.util.Date;\r\n";
										other_import += "import org.springframework.format.annotation.DateTimeFormat;\r\n";
									}
									fmt = "\r\n\t@DateTimeFormat(pattern = \"yyyy-MM-dd\")";
								}
								if(StringUtil.isNotEmpty(length))
									column_attr.append("length=").append(length);
								
								List<String> many = table.getMany();
								
								boolean ismany = false;
								if(many!=null&many.size()>0){
									for (String m : many) {
										String tab = m.split("@")[0];
										String mtab = m.split("@")[1];
										String mcol = m.split("@")[2];
										if(tab.equals(table.getName())&&mcol.equals(column.getName())){
											ismany = true;
											String temp = colFieldTemp;
											temp = "\r\n\t/** " + column.getNotes() + " */" +
													"\r\n\tprivate "+StringUtil.upFirstChar(mtab)+" "+mtab+";\r\n";
											colField.append(temp);
											
											String attrtemp = "\r\n\t@ManyToOne(fetch = FetchType.LAZY)" +
														"\r\n\t@JoinColumn(name = \""+mcol+"\")";
											colAttr.append(attrtemp);
											colAttr.append(colAttrTemp.replace("<column_name_b>", StringUtil.upFirstChar(mtab))
													.replace("<COL_ID>"+idFieldTemp+"</COL_ID>", idField)
													.replace("<column_notes>", column.getNotes())
													.replace("@Column(<column_attr>)\r\n\t", "")
													.replace("<column_type>", StringUtil.upFirstChar(mtab))
													.replace("<column_name>", mtab));
											
											if(!other_import.contains("javax.persistence.ManyToOne")){
												other_import += "import javax.persistence.ManyToOne;\r\n";
											}
											if(!other_import.contains("javax.persistence.JoinColumn")){
												other_import += "import javax.persistence.JoinColumn;\r\n";
											}
											if(!other_import.contains("javax.persistence.FetchType")){
												other_import += "import javax.persistence.FetchType;\r\n";
											}
										}
									}
								}
								
								if(!ismany){
									colField.append(colFieldTemp.replace("<COL_ID>"+idFieldTemp+"</COL_ID>", idField)
											.replace("<column_notes>", column.getNotes())
											.replace("<column_attr>", column_attr)
											.replace("<column_type>", etype)
											.replace("<fmt>", fmt)
											.replace("<column_name>", column.getName()));
									
									colAttr.append(colAttrTemp.replace("<column_name_b>", StringUtil.upFirstChar(column.getName()))
											.replace("<COL_ID>"+idFieldTemp+"</COL_ID>", idField)
											.replace("<column_notes>", column.getNotes())
											.replace("<column_attr>", column_attr)
											.replace("<column_type>", etype)
											.replace("<column_name>", column.getName()));
								}
							}
							List<String> one = table.getOne();
							if(one!=null&one.size()>0){
								colField.append("\r\n\t/** 多表关系映射 */");
								for (String obj : one) {
									String o = obj.split("@")[0];
									String mapping = obj.split("@")[1];
									
									colAttr.append("\r\n\t@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = \"").append(mapping).append("\")");
									colField.append("\r\n\tprivate Set<").append(StringUtil.upFirstChar(o)).append("> ").append(o).append("s = new HashSet<").append(StringUtil.upFirstChar(o)).append(">(0);\r\n");
									
									String idFieldTemp = StringUtil.getMarkString(colAttrTemp, "<COL_ID>", "</COL_ID>");
									colAttr.append(colAttrTemp.replace("<column_name_b>", StringUtil.upFirstChar(o)+"s")
											.replace("<COL_ID>"+idFieldTemp+"</COL_ID>", "")
											.replace("@Column(<column_attr>)\r\n\t", "")
											.replace("@AComment(comment=\"<column_notes>\")\r\n\t", "")
											.replace("<column_type>", "Set<"+StringUtil.upFirstChar(o)+">")
											.replace("<column_name>", o+"s"));
									
									if(!other_import.contains("javax.persistence.OneToMany")){
										other_import += "import javax.persistence.OneToMany;\r\n";
									}
									if(!other_import.contains("java.util.Set")){
										other_import += "import java.util.Set;\r\n";
									}
									if(!other_import.contains("java.util.HashSet")){
										other_import += "import java.util.HashSet;\r\n";
									}
									if(!other_import.contains("javax.persistence.CascadeType")){
										other_import += "import javax.persistence.CascadeType;\r\n";
									}
									if(!other_import.contains("javax.persistence.FetchType")){
										other_import += "import javax.persistence.FetchType;\r\n";
									}
								}
							}
						}
					}else if(dbType.equals(DBType.oracle)){
						//TODO ORACLE解析
					}
					entityTemp = entityTemp.replace(colFieldTemp, colField)
							.replace(colAttrTemp, colAttr)
							.replace("<package>", schemaname.replace("\\", "."))
							.replace("<other_import>", other_import)
							.replace("<COL>", "").replace("</COL>", "");
					
					if(isoverwrite){
						StringUtil.write(System.getProperty("user.dir")+"\\src\\main\\java\\com\\" + schemaname
								+ "\\entity\\" + StringUtil.upFirstChar(table.getName()) + ".java", entityTemp);
					}else{
						StringUtil.write(System.getProperty("user.dir")+"\\gen"
								+ "\\entity\\" + StringUtil.upFirstChar(table.getName()) + ".java", entityTemp);
					}
					
				}
			}
		}
		System.out.println(tabName + "-生成完成！");
	}
	
	/**
	 * 设置表关系
	 * @author 云
	 */
	private static void setConstraint(Table table){
		try{
			String constraintSQL = "";
			if(dbType.equals(DBType.mysql)){
				constraintSQL = SQLHelper.CONSTRAINT_MYSQL;
			}else if(dbType.equals(DBType.oracle)){
				constraintSQL = SQLHelper.CONSTRAINT_ORACLE;
			}
			constraintSQL = constraintSQL.replace("<dbName>", dbName).replace("<tabName>", table.getName());
			
			Connection conn = null;
			String url = "jdbc:mysql://localhost:3306/${dbName}?"
	                + "user=${user}&password=${pwd}&useUnicode=true&characterEncoding=UTF8";
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
	        // 一个Connection代表一个数据库连接
	        conn = DriverManager.getConnection(url);
	        
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(constraintSQL);
	        
	        List<String> many = new ArrayList<String>();
			List<String> one = new ArrayList<String>();
	        while (rs.next()) {
				String TABLE_NAME = rs.getString("TABLE_NAME");
				String COLUMN_NAME = rs.getString("COLUMN_NAME");
				String REFERENCED_TABLE_NAME = rs.getString("REFERENCED_TABLE_NAME");
				
				if(TABLE_NAME.equals(table.getName())){
					many.add(TABLE_NAME+"@"+REFERENCED_TABLE_NAME+"@"+COLUMN_NAME);//格式：表名|映射表名|映射字段名
				}else if(REFERENCED_TABLE_NAME.equals(table.getName())){
					one.add(TABLE_NAME+"@"+REFERENCED_TABLE_NAME);
				}
	        }
	        table.setMany(many);
			table.setOne(one);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 为表对象及其字段对象集合赋值
	 * @author 云
	 * @param dbType
	 * @param dbName
	 */
	private static void setTable(){
		try{
			String alltable = "";
			if(dbType.equals(DBType.mysql)){
				alltable = SQLHelper.ALLTABLE_MYSQL;
			}else if(dbType.equals(DBType.oracle)){
				alltable = SQLHelper.ALLTABLE_ORACLE;
			}
			alltable = alltable.replace("<dbName>", dbName);
			
			Connection conn = null;
			String url = "jdbc:mysql://localhost:3306/${dbName}?"
	                + "user=${user}&password=${pwd}&useUnicode=true&characterEncoding=UTF8";
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
	        // 一个Connection代表一个数据库连接
	        conn = DriverManager.getConnection(url);
	        
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(alltable);
			
			tables = new HashMap<String, Table>();
			while (rs.next()) {
				String columnsql = "";
				if(dbType.equals(DBType.mysql)){
					columnsql = SQLHelper.COLUMN_MYSQL;
				}else if(dbType.equals(DBType.oracle)){
					columnsql = SQLHelper.COLUMN_ORACLE;
				}
				columnsql = columnsql.replace("<dbName>", dbName).replace("<tabName>", rs.getString("table_name"));
				//赋值表对象
				Table table = new Table();
				table.setName(rs.getString("table_name"));
				table.setNotes(rs.getString("table_comment"));
				
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(columnsql);
				List<Column> lc = new ArrayList<Column>();
				while (rs2.next()) {
					Column column = new Column();
					column.setName(rs2.getString("column_name"));
					column.setIsnull(rs2.getString("is_nullable"));
					column.setType(rs2.getString("column_type"));
					column.setNotes(rs2.getString("column_comment"));
					column.setKey(rs2.getString("column_key"));
					column.setPrecision(rs2.getString("numeric_precision"));
					column.setScale(rs2.getString("numeric_scale"));
					lc.add(column);
				}
				//设置表关系
				setConstraint(table);
				table.setColumns(lc);
				tables.put(table.getName(), table);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * MAIN--此版本简称蛋疼版，好麻烦！！！
	 * @author 云
	 * @param args
	 */
	public static void main(String[] args){
		MavenGenEntityForHibernateConf.genAll();//生成所有表的HIBERNATE实体对象
//		GenEntityForHibernateConf.genEnt("article");//根据表名生成对应的HIBERNATE实体对象
	}
	
}
