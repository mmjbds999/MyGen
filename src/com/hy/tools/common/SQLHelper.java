package com.hy.tools.common;

public class SQLHelper {

	/** MYSQL获取指定库的所有表名 */
	public static final String ALLTABLE_MYSQL = "select table_name,table_comment from information_schema.tables t where t.table_schema='<dbName>'";
	
	/** ORACLE获取指定库的所有表名 */
	public static final String ALLTABLE_ORACLE = "";
	
	/** MYSQL获取指定库指定表的所有字段信息 */
	public static final String COLUMN_MYSQL = "select " +
			"column_name," +
			"is_nullable," +
			"column_type," +
			"column_comment," +
			"column_key," +
			"numeric_precision," +
			"numeric_scale " +
			"from information_schema.columns where table_schema ='<dbName>' and table_name = '<tabName>'";
	
	/** ORACLE获取指定库指定表的所有字段信息 */
	public static final String COLUMN_ORACLE = "";
	
	/** MYSQL获取指定库指定表的外键信息 */
	public static final String CONSTRAINT_MYSQL = "select " +
			"TABLE_NAME,COLUMN_NAME,CONSTRAINT_NAME," +
			"REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME from information_schema.KEY_COLUMN_USAGE " +
			"where constraint_schema=\"<dbName>\" and (table_name=\"<tabName>\" or REFERENCED_TABLE_NAME=\"<tabName>\") " +
			"and CONSTRAINT_NAME<>\"PRIMARY\"";
	
	/** ORACLE获取指定库指定表的外键信息 */
	public static final String CONSTRAINT_ORACLE = "";
	
}
