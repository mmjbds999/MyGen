package com.hy.tools.uitl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import com.hy.tools.exception.FreeMarkException;
import com.hy.tools.uitl.StringTemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * <pre>
 * Freemarker辅助类
 * </pre>
 * 
 * @author 黄云
 * 2015年11月11日
 */
public class FreemarkerUtil {

	/**
	 * 获取模板字符串
	 * @param templetstr
	 * @param params
	 * @param request
	 * @return
	 */
	public static String getTemplate(String templetstr, Map<String, Object> params) {
		Template template = getTemplate(templetstr);
		StringWriter writer = new StringWriter();
		try {
			template.process(params, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String rv = writer.toString();
		return rv;
	}
	
	/**
	 * 获取模板
	 * @param templetstr
	 * @return
	 */
	private static Template getTemplate(String templetstr) {
		Template template = null;
		Configuration cfg = new Configuration();
		try {
			cfg.setEncoding(Locale.CHINA, "UTF-8");
			cfg.setTemplateLoader(new StringTemplateLoader(templetstr));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setTemplateExceptionHandler(new FreeMarkException());
			template = cfg.getTemplate("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}
	
}
