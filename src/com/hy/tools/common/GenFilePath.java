package com.hy.tools.common;

/**
 * <pre>
 * 生成文件路径
 * </pre>
 * 
 * @author 黄云
 * 2015年11月6日
 */
public interface GenFilePath {

	/**
	 * 页面文件夹路径
	 */
	public static String pageFolder = "/gen/jsp/";
	
	/**
	 * Include文件夹路径
	 */
	public static String includeFolder = "/gen/includes/";
	
	/**
	 * Include文件夹路径-True
	 */
	public static String trueTncludeFolder = "/includes/";
	
	/**
	 * JS文件夹路径
	 */
	public static String jsFolder = "/gen/js/";
	
	/**
	 * 页面文件夹路径
	 */
	public static String classFolder = "/gen/controller/";
	
	/**
	 * 页面文件夹路径
	 */
	public static String formFolder = "/gen/forms/";
	
	/**
	 * 页面文件夹路径
	 */
	public static String voFolder = "/gen/vo/";
	
	
	//---------------------------------------下面是创建项目所需的--------------------------------------------
	
	/**
	 * build
	 */
	public static String build = "/build/";
	
	/**
	 * prop
	 */
	public static String prop = "/prop/";
	
	/**
	 * conf
	 */
	public static String conf = "/conf/";
	
}
