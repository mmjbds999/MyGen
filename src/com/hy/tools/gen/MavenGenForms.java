package com.hy.tools.gen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hy.tools.annotaion.AListObj;
import com.hy.tools.annotaion.AModelName;
import com.hy.tools.annotaion.AOne2Many;
import com.hy.tools.annotaion.ASearchObj;
import com.hy.tools.annotaion.AValid;
import com.hy.tools.bean.Column;
import com.hy.tools.common.GenFilePath;
import com.hy.tools.common.TemplatePath;
import com.hy.tools.enums.SaveInputType;
import com.hy.tools.enums.SearchInputType;
import com.hy.tools.enums.ValidEnum;
import com.hy.tools.uitl.FreemarkerUtil;
import com.hy.tools.uitl.PackageUtil;
import com.hy.tools.uitl.StringUtil;
import com.hy.tools.uitl.TimeUtil;

/**
 * <pre>
 * 根据VO生成forms
 * </pre>
 *
 * @author 黄云
 * 2015年11月11日
 */
public class MavenGenForms {

	public static String genPath = "${path}${projectName}";

	public static String formPath = null;

	public static String formPackageName = null;

	public static String jspPath = "\\src\\main\\webapp\\jsp\\";

	public static String classPath = "\\src\\main\\java\\${packagePath}\\";

	public static boolean genToTruePath = true;

	/**
	 * 生成forms
	 */
	public static void genList(Class<?> clazz){
		String pageName = "x";
		if(clazz.isAnnotationPresent(AModelName.class)){
			pageName = clazz.getAnnotation(AModelName.class).pageName();
			
			Field[] fields = clazz.getDeclaredFields();
			List<Column> searchList = new ArrayList<Column>();
			List<Column> addList = new ArrayList<Column>();
			for (Field field : fields) {
				//column
				Column column = setColumn(field);
				if(column!=null){
					//生成添加界面
					addList.add(column);

					//生成search字段
					if(field.isAnnotationPresent(ASearchObj.class)){
						searchList.add(column);
					}
				}
			}

			genForms(pageName, searchList, addList);//生成Form
		}
	}
	
	/**
	 * 设置column属性
	 * @param field
	 * @return
	 */
	private static Column setColumn(Field field){
		Column column = new Column();
		column.setName(field.getName());
		column.setType(field.getType());
		column.setTypeStr(field.getType().getSimpleName());
		column.setTypeName(field.getType().getName());
		column.setNameB(StringUtil.upFirstChar(field.getName()));
		//one2many所需
		if(field.isAnnotationPresent(AOne2Many.class)){
			column.setUserbtn(field.getAnnotation(AOne2Many.class).userBtn());
			column.setQname(field.getAnnotation(AOne2Many.class).manyName());
			column.setVoName(column.getName().substring(0,column.getName().length()-1));

			column.setSaveType("");
			column.setOptionName("");
			
			return column;
		}
		if(field.isAnnotationPresent(AListObj.class)){
			column.setComment(field.getAnnotation(AListObj.class).comment());
			column.setLength(field.getAnnotation(AListObj.class).length());
			column.setSaveType(field.getAnnotation(AListObj.class).cType().getName());
			column.setViweType(field.getAnnotation(AListObj.class).vType().getName());
			column.setOptionName(field.getAnnotation(AListObj.class).optionName());

			//one2many所需
			if(field.isAnnotationPresent(AOne2Many.class)){
				column.setUserbtn(field.getAnnotation(AOne2Many.class).userBtn());
				column.setVoName(column.getName().substring(0,column.getName().length()-1));
			}
			
			//jquery验证所需
			if(field.isAnnotationPresent(AValid.class)){
				ValidEnum[] enums = field.getAnnotation(AValid.class).valids();
				List<String> valids = new ArrayList<String>();
				for (ValidEnum validEnum : enums) {
					valids.add(validEnum.getName());
				}
				column.setValids(valids);
				column.setParam(field.getAnnotation(AValid.class).param());
			}

			//生成search字段
			if(field.isAnnotationPresent(ASearchObj.class)){
				column.setShowType(field.getAnnotation(ASearchObj.class).type().getName());
				column.setVoField(field.getAnnotation(ASearchObj.class).voField());
				if(field.getAnnotation(ASearchObj.class).type()==SearchInputType.SELECT){
					Class<?> c = field.getAnnotation(ASearchObj.class).selectEnum();
					for (Method method : c.getDeclaredMethods()) {
						if(method.getName().equals("values")){
							try {
								Enum<?>[] eunms = (Enum<?>[]) method.invoke(c.getClass());
								List<Integer> codes = new ArrayList<Integer>();
								List<String> names = new ArrayList<String>();
								for (Enum<?> e : eunms) {
									Integer code = (Integer)e.getClass().getMethod("getCode").invoke(e);
									String name = (String)e.getClass().getMethod("getName").invoke(e);
									if(code!=-100){
										codes.add(code);
										names.add(name);
									}
								}
								column.setCodes(codes);
								column.setNames(names);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			column.setVoFieldName(field.getAnnotation(AListObj.class).selectVoName());
			//需要关联表展现数据选项的东东
			if(field.getAnnotation(AListObj.class).cType()==SaveInputType.SELECTVO
					|| field.getAnnotation(AListObj.class).cType()==SaveInputType.CHECKBOXVO){
				Class<?> c = field.getAnnotation(AListObj.class).selectVo();
				String voName = StringUtil.lowFirstChar(c.getSimpleName());
				if(voName.contains("Response")){
					voName = voName.substring(0, voName.indexOf("Response"));
				}
				if(voName.contains("Vo")){
					voName = voName.substring(0, voName.indexOf("Vo"));
				}
				column.setVoTable(voName);
				column.setVoTableB(StringUtil.upFirstChar(voName));
				column.setVoName(voName+"/all.do");
				column.setVoFieldNameB(StringUtil.upFirstChar(field.getAnnotation(AListObj.class).selectVoName()));
			}

			//使用枚举的数据列展示
			if(field.getAnnotation(AListObj.class).cType()==SaveInputType.SELECT
					|| field.getAnnotation(AListObj.class).cType()==SaveInputType.RADIO
					|| field.getAnnotation(AListObj.class).cType()==SaveInputType.CHECKBOX
					|| field.getAnnotation(AListObj.class).cType()==SaveInputType.BACKGROUND){
				Class<?> c = field.getAnnotation(AListObj.class).selectEnum();
				column.setEnumName(c.getSimpleName());
				for (Method method : c.getDeclaredMethods()) {
					if(method.getName().equals("values")){
						try {
							Enum<?>[] eunms = (Enum<?>[]) method.invoke(c.getClass());
							List<Integer> codes = new ArrayList<Integer>();
							List<String> names = new ArrayList<String>();
							List<String> colors = new ArrayList<String>();
							for (Enum<?> e : eunms) {
								Integer code = (Integer)e.getClass().getMethod("getCode").invoke(e);
								if(field.getAnnotation(AListObj.class).cType()==SaveInputType.BACKGROUND){
									String color = (String)e.getClass().getMethod("getColor").invoke(e);
									if(!color.equals("unknow")){
										colors.add(color);
									}
								}
								String name = "";
								Method[] methods = e.getClass().getMethods();
								for (Method m : methods) {
									if(m.getName().equals("getName")){
										name = (String)e.getClass().getMethod("getName").invoke(e);
										break;
									}
									if(m.getName().equals("getDescription")){
										name = (String)e.getClass().getMethod("getDescription").invoke(e);
										break;
									}
								}
								if(code!=-100){
									codes.add(code);
									names.add(name);
								}
							}
							column.setSaveCodes(codes);
							column.setSaveNames(names);
							column.setSaveColors(colors);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			if(!field.getAnnotation(AListObj.class).option().contains("A")){
				column.setIsHidden(true);
				String defaultVal = "";
				if(field.getType().getSimpleName().equals("Date")){
					//defaultVal = TimeUtil.getNowTime("yyyy-MM-dd HH:mm:ss");
				}else if(field.getType().getSimpleName().equals("Integer")
						||field.getType().getSimpleName().equals("int")
						||field.getType().getSimpleName().equals("bigint")
						||field.getType().getSimpleName().equals("long")
						||field.getType().getSimpleName().toLowerCase().equals("float")
						||field.getType().getSimpleName().toLowerCase().equals("double")
						||field.getType().getSimpleName().toLowerCase().equals("decimal")){
					defaultVal = "0";
				}else if(field.getType().getSimpleName().toLowerCase().equals("boolean")){
					defaultVal = "false";
				}else if(field.getType().getName().contains(".entity.")){
					defaultVal = "${"+column.getName()+"_id }";
					column.setIsSetDefault(true);
				}
				column.setDefaultVal(defaultVal);
			}
			return column;
		}
		return null;
	}


	/**
	 * 生成Form
	 * @param pageName
	 * @param searchList
	 */
	private static void genForms(String pageName, List<Column> searchList, List<Column> addList){
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.pageListForm);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("pageName", pageName);
		if(pageName.equals("admin")){
			data.put("isAdmin", true);
		}
		String packageName = classPath.replace("\\", ".");
		packageName = packageName.substring(packageName.indexOf("com."));
		packageName = packageName.substring(0,packageName.length()-1);
		data.put("packageName", packageName);
		data.put("searchList", searchList);
		data.put("addList", addList);
		String cPage = StringUtil.upFirstChar(pageName);
		data.put("cPage", cPage);
		data.put("nowDate", TimeUtil.getNowTime("yyyy-MM-dd"));
		if(formPath!=null){
			data.put("packageName", formPackageName);
		}
		String result = FreemarkerUtil.getTemplate(template, data);
		if(genPath!=null && classPath!=null){
			if(genToTruePath){
				if(formPath!=null){
					StringUtil.write(formPath+"forms\\"+cPage+"Form.java", result);
				}else{
					StringUtil.write(genPath+classPath+"forms\\"+cPage+"Form.java", result);
				}
			}else{
				StringUtil.write(genPath+jspPath+GenFilePath.formFolder+cPage+"Form.java", result);
			}
		}else{
			StringUtil.write(System.getProperty("user.dir")+GenFilePath.formFolder+pageName+"Form.java", result);
		}
	}


	/**
	 * 根据vo生成forms
	 * @param voPath
	 */
	public static void genForms(){
		genFormsByVo(genToTruePath);
	}
	
	/**
	 * 根据vo生成forms
	 * @param voPath
	 */
	private static void genFormsByVo(boolean overwrite){
		genToTruePath = overwrite;
		String voPath = "${packageName}.vo";
		try {
			List<String> clazzs = PackageUtil.getClassName(voPath);
			if(clazzs!=null&&clazzs.size()>0){
				for (String clazzName : clazzs) {
					Class<?> clazz = Class.forName(clazzName);
					genList(clazz);
				}
			}
			System.out.println("生成完成！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
