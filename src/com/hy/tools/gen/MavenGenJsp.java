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
import com.hy.tools.annotaion.AOne2Many;
import com.hy.tools.annotaion.ASearchObj;
import com.hy.tools.annotaion.AValid;
import com.hy.tools.bean.Column;
import com.hy.tools.common.GenConfig;
import com.hy.tools.common.GenFilePath;
import com.hy.tools.common.TemplatePath;
import com.hy.tools.enums.SaveInputType;
import com.hy.tools.enums.SearchInputType;
import com.hy.tools.enums.ValidEnum;
import com.hy.tools.main.CreateMavenProject;
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
public class MavenGenJsp {

	public static String genPath = "${path}${projectName}";

	public static String formPath = null;

	public static String formPackageName = null;

	public static String jspPath = "\\src\\main\\webapp\\jsp\\";

	public static String staticPath = "\\src\\main\\webapp\\static\\";

	public static String classPath = "\\src\\main\\java\\${packagePath}\\";

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
		String visitPage = "";
		if(clazz.isAnnotationPresent(AModelName.class)){
			modelName = clazz.getAnnotation(AModelName.class).modelName();
			pageName = clazz.getAnnotation(AModelName.class).pageName();
			visitPage = clazz.getAnnotation(AModelName.class).visitPage();
			visitPage = visitPage.equals("")?pageName:visitPage;
			pageType = clazz.getAnnotation(AModelName.class).pageType().getName();
			
			if(pageName.equals("admin")&&hasField(clazz,"account")&&hasField(clazz,"pwd")){
				CreateMavenProject.genLogin();
			}

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

			genClass(pageName, visitPage, showList, addList);//生成Action
//			genForms(pageName, searchList, addList);//生成Form

			String result = FreemarkerUtil.getTemplate(template, data);
			result = result.replace("@@@", "${").replace("@@", "}");
			if(genPath!=null && jspPath!=null){
				if(genToTruePath){
					StringUtil.write(genPath+jspPath+visitPage+".jsp", result);
				}else{
					StringUtil.write(genPath+jspPath+GenFilePath.pageFolder+visitPage+".jsp", result);
				}
			}else{
				StringUtil.write(System.getProperty("user.dir")+GenFilePath.pageFolder+visitPage+".jsp", result);
			}
		}
	}
	
	private static boolean hasField(Class<?> clazz, String field){
		try {
			return clazz.getDeclaredField(field)!=null;
		} catch (NoSuchFieldException | SecurityException e) {
			return false;
		}
	}

	/**
	 * 生成Action
	 * @param pageName
	 */
	private static void genClass(String pageName,String visitPage, List<Column> showList, List<Column> addList){
		String template = StringUtil.readFile(System.getProperty("user.dir") + TemplatePath.pageListClass);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("pageName", pageName);
		data.put("showList", showList);
		data.put("addList", addList);
		data.put("visitPage", visitPage);
		data.put("bVisitPage", StringUtil.upFirstChar(visitPage));
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
				StringUtil.write(genPath+classPath+"controller\\"+StringUtil.upFirstChar(visitPage)+"Controller.java", result);
			}else{
				StringUtil.write(genPath+jspPath+GenFilePath.classFolder+StringUtil.upFirstChar(visitPage)+"Controller.java", result);
			}
		}else{
			StringUtil.write(System.getProperty("user.dir")+GenFilePath.classFolder+StringUtil.upFirstChar(visitPage)+"Controller.java", result);
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
			if(genPath!=null && staticPath!=null){
				if(genToTruePath){
					StringUtil.write(genPath+jspPath+GenFilePath.trueTncludeFolder+"left.jsp", result);
				}else{
					StringUtil.write(genPath+jspPath+GenFilePath.includeFolder+"left.js", result);
				}
			}else{
				StringUtil.write(System.getProperty("user.dir")+GenFilePath.trueTncludeFolder+"left.js", result);
			}
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
			column.setIsChild(field.getAnnotation(AListObj.class).isChild());
			column.setIsParent(field.getAnnotation(AListObj.class).isParent());
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
			
				//级联相关的
				if(field.getAnnotation(AListObj.class).isChild()){
					column.setParentName(field.getAnnotation(AListObj.class).parentName());
					column.setVoName(voName+"/query.do");
				}
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
	 * 根据vo生成全部页面
	 * @param voPath
	 */
	public static void genByVo(){
		genByVo(genToTruePath);
	}
	
	/**
	 * 根据vo生成全部页面
	 * @param voPath
	 */
	public static void genByVo(boolean overwrite){
		genToTruePath = overwrite;
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
