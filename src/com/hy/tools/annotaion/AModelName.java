package com.hy.tools.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hy.tools.enums.PageType;

/**
 * <pre>
 * 管理模块名称
 * </pre>
 * 
 * @author 黄云
 * 2015年11月11日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface AModelName {

	public abstract String modelName();
	
	public abstract String pageName();
	
	public abstract String visitPage() default "";
	
	public abstract PageType pageType() default PageType.ALL;
	
	public abstract boolean useParam() default false;
	
}
