package com.hy.tools.gen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.hy.tools.annotaion.AComment;
import com.hy.tools.common.TemplatePath;
import com.hy.tools.enums.SaveInputType;
import com.hy.tools.enums.ValidEnum;
import com.hy.tools.uitl.FreemarkerUtil;
import com.hy.tools.uitl.PackageUtil;
import com.hy.tools.uitl.StringUtil;
import com.hy.tools.uitl.TimeUtil;

/**
 * <pre>
 * 生成VO
 * </pre>
 * 
 * @author 黄云
 * 2015年12月11日
 */
public class GenVo {

	public static String voPath = "\\src\\${packagePath}\\vo\\";
	
	public static String packagePath = "${packageName}.vo";
	
	public static String packageName = "${packageName}";
	
	static boolean isoverwrite=true;
	
	/**
	 * 生成一堆VO
	 */
	public static void genVoList(){
		genVoList(isoverwrite);
	}
	
	/**
	 * 生成一堆VO
	 */
	public static void genVoList(boolean overwrite){
		isoverwrite = overwrite;
		String path = "${packageName}.entity";
		try {
			List<String> clazzs = PackageUtil.getClassName(path);
			if(clazzs!=null&&clazzs.size()>0){
				for (String clazzName : clazzs) {
					Class<?> clazz = Class.forName(clazzName);
					genVo(clazz);
				}
			}
			System.out.println("生成完成！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成一个VO
	 * @param clazz
	 */
	public static void genVo(Class<?> clazz){
		genVo(clazz ,isoverwrite);
	}
	
	/**
	 * 生成一个VO
	 * @param clazz
	 */
	public static void genVo(Class<?> clazz, boolean overwrite){
		isoverwrite = overwrite;
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.vo);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("className", clazz.getSimpleName());
		data.put("classNameL", StringUtil.lowFirstChar(clazz.getSimpleName()));
		data.put("comment", clazz.getAnnotation(AComment.class).comment());
		
		List<com.hy.tools.bean.Column> voList = new ArrayList<com.hy.tools.bean.Column>();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if(method.isAnnotationPresent(Column.class)){
				com.hy.tools.bean.Column column = new com.hy.tools.bean.Column();
				column.setNameB(method.getName().substring(3));
				column.setName(StringUtil.lowFirstChar(method.getName().substring(3)));
				column.setTypeStr(method.getReturnType().getSimpleName());
				column.setComment(method.getAnnotation(AComment.class).comment());
				column.setLength(method.getAnnotation(Column.class).length());
				
				if(method.getReturnType().getSimpleName().equals("Date")){
					try {
						Field field = clazz.getDeclaredField(StringUtil.lowFirstChar(method.getName().substring(3)));
						if(field.getAnnotation(DateTimeFormat.class).pattern().equals("yyyy-MM-dd")){
							column.setSaveType(SaveInputType.DATE.toString());
						}else if(field.getAnnotation(DateTimeFormat.class).pattern().equals("yyyy-MM-dd HH:mm:ss")){
							column.setSaveType(SaveInputType.TIME.toString());
						}
					} catch (NoSuchFieldException | SecurityException e) {
						e.printStackTrace();
					}
				}else{
					setDefaultSaveInputType(column);
				}
				
				setDefaultValid(column);
				
				voList.add(column);
			}
			if(method.isAnnotationPresent(ManyToOne.class)){
				com.hy.tools.bean.Column column = new com.hy.tools.bean.Column();
				column.setNameB(method.getName().substring(3));
				column.setName(StringUtil.lowFirstChar(method.getName().substring(3)));
				column.setTypeStr(method.getReturnType().getSimpleName());
				column.setComment(method.getAnnotation(AComment.class).comment());
				column.setLength(9);
				column.setVoName(method.getReturnType().getSimpleName());
				
				setDefaultSaveInputType(column);
				setDefaultValid(column);
				
				voList.add(column);
			}
			if(method.isAnnotationPresent(OneToMany.class)){
				com.hy.tools.bean.Column column = new com.hy.tools.bean.Column();
				column.setNameB(method.getName().substring(3));
				column.setName(StringUtil.lowFirstChar(method.getName().substring(3)));
				column.setTypeStr(method.getReturnType().getSimpleName());
				column.setLength(9);
				column.setVoName(method.getReturnType().getSimpleName());
				
				column.setSaveType(SaveInputType.ONE2MANY.getName());
				
				voList.add(column);
			}
		}
		data.put("voList", voList);
		data.put("nowDate", TimeUtil.getNowTime());
		data.put("package", packagePath);
		data.put("packageName", packageName);
		String result = FreemarkerUtil.getTemplate(template, data);
		if(isoverwrite){
			StringUtil.write(System.getProperty("user.dir")+voPath+clazz.getSimpleName()+"Vo.java", result);
		}else{
			StringUtil.write(System.getProperty("user.dir")+"\\gen\\vo\\"+clazz.getSimpleName()+"Vo.java", result);
		}
	}
	
	/**
	 * 默认input类型规则
	 * @param column
	 */
	private static void setDefaultSaveInputType(com.hy.tools.bean.Column column){
		column.setSaveType("");
		if(column.getComment().contains("图片")
				|| column.getComment().contains("照片")
				|| column.getComment().contains("图标")
				|| column.getComment().contains("头像")){
			column.setSaveType(SaveInputType.IMG.toString());
		}else if(column.getTypeStr().equals("Date")){
			column.setSaveType(SaveInputType.DATE.toString());
		}else if(column.getTypeStr().equals("String") && column.getLength()==254){
			column.setSaveType(SaveInputType.TEXTEDIT.toString());
		}else if(column.getTypeStr().equals("String") && column.getLength()>200 && column.getLength()!=254){
			column.setSaveType(SaveInputType.TEXTAREA.toString());
		}
	}
	
	/**
	 * 设置默认前端验证
	 * @param column
	 */
	private static void setDefaultValid(com.hy.tools.bean.Column column){
		column.setValids(new ArrayList<String>());
		if(column.getComment().contains("手机")){
			List<String> valids = new ArrayList<String>();
			valids.add(ValidEnum.REQUIRED.toString());
			valids.add(ValidEnum.MOBILE.toString());
			column.setValids(valids);
		}
		if(column.getComment().contains("邮箱")){
			List<String> valids = new ArrayList<String>();
			valids.add(ValidEnum.REQUIRED.toString());
			valids.add(ValidEnum.EMAIL.toString());
			column.setValids(valids);
		}
		if(column.getComment().contains("身份证")){
			List<String> valids = new ArrayList<String>();
			valids.add(ValidEnum.REQUIRED.toString());
			valids.add(ValidEnum.CARD.toString());
			column.setValids(valids);
		}
		if(column.getComment().contains("组织机构代码")){
			List<String> valids = new ArrayList<String>();
			valids.add(ValidEnum.REQUIRED.toString());
			valids.add(ValidEnum.ORGCODE.toString());
			column.setValids(valids);
		}
		if(column.getTypeStr().equals("Integer")){
			List<String> valids = new ArrayList<String>();
			valids.add(ValidEnum.REQUIRED.toString());
			valids.add(ValidEnum.INT.toString());
			column.setValids(valids);
		}
		if(column.getTypeStr().equals("Double") || column.getTypeStr().equals("Float")){
			List<String> valids = new ArrayList<String>();
			valids.add(ValidEnum.REQUIRED.toString());
			valids.add(ValidEnum.NUMBER.toString());
			column.setValids(valids);
		}
	}
	
	/**
	 * MAIN--妈蛋
	 * @param args
	 */
	public static void main(String[] args) {
		genVoList(false);
	}
	
}
