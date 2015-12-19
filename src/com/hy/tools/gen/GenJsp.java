package com.hy.tools.gen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hy.tools.annotaion.AListObj;
import com.hy.tools.annotaion.AModelName;
import com.hy.tools.annotaion.ASearchObj;
import com.hy.tools.annotaion.AValid;
import com.hy.tools.bean.Column;
import com.hy.tools.common.GenConfig;
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
 * 生成JSP页面
 * </pre>
 *
 * @author 黄云
 * 2015年11月11日
 */
public class GenJsp {

	public static String genPath = "${path}${projectName}";

	public static String formPath = null;

	public static String formPackageName = null;

	public static String jspPath = "\\WebRoot\\jsp\\";

	public static String staticPath = "\\WebRoot\\static\\";

	public static String classPath = "\\src\\${packagePath}\\";

	public static boolean genToTruePath = true;

	/**
	 * 列表页生成
	 * @param modName bean模块名称
	 */
	public static void genList(Class<?> clazz){
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.pageList);
		Map<String, Object> data = new HashMap<String, Object>();

		String modelName = "";
		String pageName = "x";
		String pageType = "";
		if(clazz.isAnnotationPresent(AModelName.class)){
			modelName = clazz.getAnnotation(AModelName.class).modelName();
			pageName = clazz.getAnnotation(AModelName.class).pageName();
			pageType = clazz.getAnnotation(AModelName.class).pageType().getName();

			data.put("modNameCN", GenConfig.projectNameCN);//项目中文名
			data.put("modName", modelName);
			data.put("pageName", pageName);
			data.put("pageType", pageType);

			Field[] fields = clazz.getDeclaredFields();
			List<Column> searchList = new ArrayList<Column>();
			List<Column> showList = new ArrayList<Column>();
			List<Column> addList = new ArrayList<Column>();
			for (Field field : fields) {

				//column
				Column column = setColumn(field);
				if(column!=null){
					//生成添加界面
					addList.add(column);

					//生成列表头字段
					if(field.isAnnotationPresent(AListObj.class)
							&& field.getAnnotation(AListObj.class).option().contains("S")){
						showList.add(column);
					}

					//生成search字段
					if(field.isAnnotationPresent(ASearchObj.class)){
						searchList.add(column);
					}
				}
			}
			data.put("searchList", searchList);
			data.put("addList", addList);
			data.put("showList", showList);

			genClass(pageName, showList, addList);//生成Action
			genForms(pageName, searchList);//生成Form

			String result = FreemarkerUtil.getTemplate(template, data);
			result = result.replace("@@@", "${").replace("@@", "}");
			if(genPath!=null && jspPath!=null){
				if(genToTruePath){
					StringUtil.write(genPath+jspPath+pageName+".jsp", result);
				}else{
					StringUtil.write(genPath+jspPath+GenFilePath.pageFolder+pageName+".jsp", result);
				}
			}else{
				StringUtil.write(System.getProperty("user.dir")+GenFilePath.pageFolder+pageName+".jsp", result);
			}
		}
	}

	/**
	 * 生成Action
	 * @param pageName
	 */
	private static void genClass(String pageName, List<Column> showList, List<Column> addList){
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.pageListClass);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("pageName", pageName);
		data.put("showList", showList);
		data.put("addList", addList);
		String packageName = classPath.replace("\\", ".");
		packageName = packageName.substring(packageName.indexOf("com."));
		packageName = packageName.substring(0,packageName.length()-1);
		data.put("packageName", packageName);
		data.put("packageNameForm", packageName);
		if(formPath!=null){
			data.put("packageNameForm", formPackageName);
		}
		String cPage = StringUtil.upFirstChar(pageName);
		data.put("cPage", cPage);
		data.put("nowDate", TimeUtil.getNowTime("yyyy-MM-dd"));
		String result = FreemarkerUtil.getTemplate(template, data);
		if(genPath!=null && classPath!=null){
			if(genToTruePath){
				StringUtil.write(genPath+classPath+"controller\\"+cPage+"Controller.java", result);
			}else{
				StringUtil.write(genPath+jspPath+GenFilePath.classFolder+cPage+"Controller.java", result);
			}
		}else{
			StringUtil.write(System.getProperty("user.dir")+GenFilePath.classFolder+pageName+"Controller.java", result);
		}
	}

	/**
	 * 生成Form
	 * @param pageName
	 * @param searchList
	 */
	private static void genForms(String pageName, List<Column> searchList){
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
	 * 复制生成公用模板
	 */
	private static void copyInclude(){
		copyInclude(TemplatePath.css, "css");
		copyInclude(TemplatePath.js, "js");
		copyInclude(TemplatePath.header, "header");
		copyInclude(TemplatePath.footer, "footer");
//		copyInclude(TemplatePath.left, "left");
		copyInclude(TemplatePath.pager, "pager");
		genJS(TemplatePath.commonjs, "base");
	}

	/**
	 * 复制生成公用模板
	 */
	private static void copyInclude(String tmpPath, String pageName){
		String template = StringUtil.readFile(System.getProperty("user.dir") + tmpPath);
		Map<String, Object> data = new HashMap<String, Object>();
		String result = FreemarkerUtil.getTemplate(template, data);
		result = result.replace("@@@", "${").replace("@@", "}");
		if(genPath!=null && jspPath!=null){
			if(genToTruePath){
				StringUtil.write(genPath+jspPath+GenFilePath.trueTncludeFolder+pageName+".jsp", result);
			}else{
				StringUtil.write(genPath+jspPath+GenFilePath.includeFolder+pageName+".jsp", result);
			}
		}else{
			StringUtil.write(System.getProperty("user.dir")+GenFilePath.includeFolder+pageName+".jsp", result);
		}
	}
	
	/**
	 * 生成左侧菜单
	 */
	private static void genLeft(List<String> clazzs){
		try{
			JSONArray ja = new JSONArray();
			if(clazzs!=null&&clazzs.size()>0){
				for (String clazzName : clazzs) {
					Class<?> clazz = Class.forName(clazzName);
					JSONObject jo = new JSONObject();
					jo.put("modelName", clazz.getAnnotation(AModelName.class).pageName());
					jo.put("modelNameCN", clazz.getAnnotation(AModelName.class).modelName());
					ja.add(jo);
				}
			}
			String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.left);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("modelList", ja);
			String result = FreemarkerUtil.getTemplate(template, data);
			StringUtil.write(genPath+jspPath+GenFilePath.trueTncludeFolder+"left.jsp", result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 复制生成通用JS
	 * @param tmpPath
	 * @param pageName
	 */
	private static void genJS(String tmpPath, String pageName){
		String template = StringUtil.readFile(System.getProperty("user.dir") + tmpPath);
		Map<String, Object> data = new HashMap<String, Object>();
		String result = FreemarkerUtil.getTemplate(template, data);
		if(genPath!=null && staticPath!=null){
			if(genToTruePath){
				StringUtil.write(genPath+staticPath+GenFilePath.jsFolder+pageName+".js", result);
			}else{
				StringUtil.write(genPath+jspPath+GenFilePath.jsFolder+pageName+".js", result);
			}
		}else{
			StringUtil.write(System.getProperty("user.dir")+GenFilePath.jsFolder+pageName+".js", result);
		}
	}

	/**
	 * 设置column属性
	 * @param field
	 * @return
	 */
	private static Column setColumn(Field field){
		if(field.isAnnotationPresent(AListObj.class)){
			Column column = new Column();
			column.setName(field.getName());
			column.setType(field.getType());
			column.setTypeStr(field.getType().getSimpleName());
			column.setTypeName(field.getType().getName());
			column.setNameB(StringUtil.upFirstChar(field.getName()));

			column.setComment(field.getAnnotation(AListObj.class).comment());
			column.setLength(field.getAnnotation(AListObj.class).length());
			column.setSaveType(field.getAnnotation(AListObj.class).cType().getName());
			column.setViweType(field.getAnnotation(AListObj.class).vType().getName());
			column.setOptionName(field.getAnnotation(AListObj.class).optionName());

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
					defaultVal = TimeUtil.getNowTime("yyyy-MM-dd HH:mm:ss");
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
				}
				column.setDefaultVal(defaultVal);
			}
			return column;
		}
		return null;
	}

	/**
	 * 根据vo生成全部页面
	 * @param voPath
	 */
	public static void genByVo(){
		String voPath = "${packageName}.vo";
		try {
			List<String> clazzs = PackageUtil.getClassName(voPath);
			if(clazzs!=null&&clazzs.size()>0){
				for (String clazzName : clazzs) {
					Class<?> clazz = Class.forName(clazzName);
					genList(clazz);
				}
				genLeft(clazzs);
			}
			copyInclude();
			System.out.println("生成完成！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		genPath = "G:\\JavaSave2015\\LinZiApp";//生成路径
//		formPath = "G:\\JavaSave2015\\AppRMI\\src\\main\\java\\com\\linzi\\app\\appserver\\rmi\\";
//		formPackageName = "com.linzi.app.appserver.rmi";//这个是伴随formPath指定的，主要这个项目太麻烦，特别定义了这个路径
//		jspPath = "\\WebRoot\\jsp\\";//JSP路径
//		staticPath = "\\WebRoot\\static\\";//静态文件路径
//		classPath = "\\src\\com\\linzi\\app\\";//Action路径
//		genToTruePath = true;//此属性很重要，是否生成到真实的文件路径，一半只在第一次或者没有手动改过文件的时候设为true，改过文件最好就把此属性设为false

	}

}
