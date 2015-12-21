package com.hy.tools.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hy.tools.enums.SearchInputType;

/**
 * <pre>
 * 列表字段标识
 * </pre>
 * 
 * @author 黄云
 * 2015年11月11日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE , ElementType.FIELD })
public @interface ASearchObj {
	public abstract SearchInputType type() default SearchInputType.TEXT;
	public abstract Class<?> selectEnum() default Object.class;
	public abstract String voField() default "";
}
