package com.hy.tools.common;

/**
 * <pre>
 * 模板路径
 * </pre>
 * 
 * @author 黄云
 * 2015年11月6日
 */
public interface TemplatePath {

	/**
	 * 带查询及分页的列表页
	 */
	public static final String pageList = "/template/pageList.jsp";
	
	/**
	 * 带查询及分页的列表页--action
	 */
	public static final String pageListClass = "/template/class/Controller.java";
	
	/**
	 * 带查询及分页的列表页--form
	 */
	public static final String pageListForm = "/template/class/Form.java";
	
	/**
	 * Entity模板
	 */
	public static final String entity = "/template/class/entity.java";
	
	/**
	 * Entity模板--注解在字段上
	 */
	public static final String entity_field = "/template/class/entity_field.java";
	
	/**
	 * VO模板
	 */
	public static final String vo = "/template/class/VO.java";
	
	/**
	 * DAO模板
	 */
	public static final String dao = "/template/class/dao.java";
	
	/**
	 * Service模板
	 */
	public static final String service = "/template/class/service.java";
	
	/**
	 * 样式模板
	 */
	public static final String css = "/template/include/css.jsp";
	
	/**
	 * JS模板
	 */
	public static final String js = "/template/include/js.jsp";
	
	/**
	 * 头模板
	 */
	public static final String header = "/template/include/header.jsp";
	
	/**
	 * 左侧菜单模板
	 */
	public static final String left = "/template/include/left.jsp";
	
	/**
	 * 底部信息模板
	 */
	public static final String footer = "/template/include/footer.jsp";
	
	/**
	 * 分页模板
	 */
	public static final String pager = "/template/include/pager.jsp";
	
	/**
	 * 通用JS
	 */
	public static final String commonjs = "/template/js/base.js";
	
	
	
	
	
	//---------------------------------------下面是创建项目所需的--------------------------------------------
	
	/**
	 * build.properties
	 */
	public static final String build_prop = "/template/build/log4j.properties";
	
	/**
	 * build.xml
	 */
	public static final String build_xml = "/template/build/log4j.properties";
	
	/**
	 * log4j
	 */
	public static final String log4j = "/prop/log4j.properties";
	
	/**
	 * project.properties
	 */
	public static final String project_prop = "/prop/project.properties";
	
	/**
	 * all.xml
	 */
	public static final String all = "/conf/all.xml";
	
	/**
	 * beanForDao.xml
	 */
	public static final String beanForDao = "/conf/beanForDao.xml";
	
	/**
	 * beanForMVC.xml
	 */
	public static final String beanForMVC = "/conf/beanForMVC.xml";
	
	/**
	 * beanForService.xml
	 */
	public static final String beanForService = "/conf/beanForService.xml";
	
	/**
	 * client-webService.xml
	 */
	public static final String webService = "/conf/client-webService.xml";
	
	/**
	 * dataAccess.properties
	 */
	public static final String dataAccess = "/conf/dataAccess.properties";
	
}
