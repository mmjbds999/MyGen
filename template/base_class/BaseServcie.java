package ${packageName}.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linzi.framework.db.PageQueryResult;
import com.linzi.framework.utils.DateUtil;
import com.linzi.framework.utils.LogUtil;
import com.linzi.framework.utils.StringUtils;

/**
 * <pre>
 * 业务基类
 * </pre>
 * 
 * @author 黄云
 * 2015-6-15
 */
public class BaseServcie<T> {

	protected static final Log log = LogFactory.getLog(BaseServcie.class);
	
	/**
	 * 输出结果对象
	 * @param issuccess
	 * @param message
	 * @param obj
	 * @return
	 */
	protected JSONObject result(boolean issuccess,String message,Object obj) {
		JSONObject result = new JSONObject();
		result.put("code", issuccess?200:201);
		result.put("success", issuccess);
		result.put("message", message);
		result.put("data", obj);
		return result;
	}
	
	/**
	 * 输出结果对象
	 * @param issuccess
	 * @param message
	 * @return
	 */
	protected JSONObject result(boolean issuccess,String message) {
		JSONObject result = new JSONObject();
		result.put("code", issuccess?200:201);
		result.put("success", issuccess);
		result.put("message", message);
		result.put("data", null);
		return result;
	}
	
	/**
	 * model需要转换的字段在这里处理下
	 * @param ent
	 * @return
	 */
	protected T changeModel(T ent) {
		Method[] methods = ent.getClass().getMethods();
		for (Method method : methods) {
			if(method.isAnnotationPresent(Column.class)){
				if(method.getAnnotation(Column.class).length()==14||method.getAnnotation(Column.class).length()==8){
					Method setter;
					try {
						setter = ent.getClass().getDeclaredMethod("set"+method.getName().substring(3), method.getReturnType());
						Object val = method.invoke(ent);
						setter.invoke(ent, val.toString().replace("-", "").replace(" ", "").replace(":", ""));
					} catch (Exception e) {
						LogUtil.traceError(log, e);
					} 
				}
			}
		}
		return ent;
	}
	
	/**
	 * po 集合 转 Json 集合
	 * 
	 * @param po
	 * @param jo
	 * @param isopenLazy 
	 * @return
	 */
	protected JSONArray po2JsonList(List<?> poList, boolean isopenLazy){
		JSONArray ja = new JSONArray();
		if(poList!=null&&poList.size()>0){
			for (Object po : poList) {
				JSONObject jo = po2Json(po, isopenLazy);
				ja.add(jo);
			}
		}
		return ja;
	}
	
	/**
	 * page 对象转 Json
	 * 
	 * @param po
	 * @param jo
	 * @param isopenLazy
	 * @return
	 */
	public JSONObject po2JsonPage(PageQueryResult<?> page, boolean isopenLazy) {
		JSONObject jpage = new JSONObject();
		JSONArray ja = po2JsonList(page.getList(), isopenLazy);
		jpage.put("list", ja);
		jpage.put("pageList", page.getPageList());
		jpage.put("pageList", page.getPageList());
		jpage.put("pageNo", page.getPageNo());
		jpage.put("pageSize", page.getPageSize());
		jpage.put("pageTotal", page.getPageTotal());
		jpage.put("prev", page.getPrev());
		jpage.put("next", page.getNext());
		return jpage;
	}
	
	/**
	 * po 转 Json 
	 * 排除Set集合在外
	 * 此方法会将延时加载的东西都获取出来，偷懒方法，建议少用，ManyToOne对象多的话对效率会有影响！
	 * 少的话还是很好用的，☺
	 * 
	 * @param po
	 * @param jo
	 * @param isopenLazy
	 * @return
	 */
	protected JSONObject po2Json(Object po, boolean isopenLazy){
		JSONObject jo = new JSONObject();
		doPo2Json(po, jo, isopenLazy);
		return jo;
	}
	
	/**
	 * 解析Hibernate查询返回为Object[]的对象转换成适用的json
	 * 
	 * @param obj
	 * @return
	 */
	protected JSONObject poObj2Json(Object[] obj) {
		objNames = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		if (obj != null && obj.length > 0) {
			for (Object object : obj) {
				JSONObject jo = new JSONObject();
				doPo2Json(object, jo, false);
				json.put(getObjSrcName(object), jo);
			}
		}
		return json;
	}
	
	/**
	 * 解析Hibernate查询返回为List<Object[]>的对象转换成适用的json
	 * 
	 * @param obj
	 * @return
	 */
	protected JSONArray poObjList2Json(List<Object[]> list) {
		JSONArray ja = new JSONArray();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				JSONObject jo = poObj2Json(obj);
				ja.add(jo);
			}
		}
		return ja;
	}
	
	private static Map<String, Object> objNames;
	
	/**
	 * 根据Objct获取原生类型名称解析成适用的名称
	 * 
	 * @param obj
	 * @return
	 */
	private String getObjSrcName(Object obj) {
		if(obj==null){
			return "";
		}
		String srcType = obj.getClass().getName();
		srcType = StringUtils.lowerFirstChar(srcType.substring(srcType.lastIndexOf(".T")+1));
		if(objNames.containsKey(srcType)){
			Integer index = Integer.parseInt(objNames.get(String.format("%s_index", srcType)).toString()) + 1;
			objNames.put(String.format("%s_index", srcType), index);
			return String.format("%s%s", srcType, index);
		}else{
			objNames.put(srcType, srcType);
			objNames.put(String.format("%s_index", srcType), 0);
			return srcType;
		}
	}

	/**
	 * 递归执行po2Json
	 * @param po
	 * @param jo
	 * @param isopenLazy
	 */
	private void doPo2Json(Object po, JSONObject jo, boolean isopenLazy){
		if(po!=null){
			Method[] methods = po.getClass().getDeclaredMethods();
			try {
				if(methods!=null&&methods.length>0){
					for (Method method : methods) {
						if(method.getName().startsWith("get")&&method.getName().length()>3
								&&!method.getName().contains("Handler")
								&&!method.getName().contains("HibernateLazyInitializer")){
							Object val = method.invoke(po);
							if(method.getReturnType()!=Set.class){
								if(method.getReturnType().toString().contains(".entity")){
									if(!isPoNull(val)&&isopenLazy){
										JSONObject jop = new JSONObject();
										doPo2Json(val, jop, isopenLazy);
										jo.put(StringUtils.lowerFirstChar(method.getName().substring(3)), jop);
									}
									if(isPoNotNull(val)){
										JSONObject jop = new JSONObject();
										doPo2Json(val, jop, isopenLazy);
										jo.put(StringUtils.lowerFirstChar(method.getName().substring(3)), jop);
									}
								}else{
									if(method.getReturnType()==Date.class){
										val = DateUtil.dateFormat((Date)val, "yyyy-MM-dd HH:mm:ss");
									}
									jo.put(StringUtils.lowerFirstChar(method.getName().substring(3)), val);
								}
							}
						}
						if(method.getName().equals("isBol")){
							//给异步分页用的
							Object val = method.invoke(po);
							jo.put(StringUtils.lowerFirstChar(method.getName().substring(2)), val);
						}
					}
				}
			} catch (Exception e) {
				LogUtil.traceError(log, e);
			} 
		}
	}
	
	/**
	 * 判断一个po是否为null--如果id为空，默认此po为null
	 * @param po
	 * @return
	 */
	private boolean isPoNull(Object po){
		try {
			if(po!=null){
				Method m = po.getClass().getMethod("getId");
				if(m.invoke(po)==null){
					return true;
				}
			}
		} catch (Exception e) {
			LogUtil.traceError(log, e);
		}
		return false;
	}
	
	/**
	 * 判断PO是否在查询时已赋值
	 * @param po
	 * @return
	 */
	private boolean isPoNotNull(Object po){
		try {
			if(po!=null){
				Field[] field = po.getClass().getDeclaredFields();
				if(field!=null&&field.length>0){
					for (Field f : field) {
						if(!f.getName().equals("id")
								&&!f.getName().contains("serialVersionUID")
								&&f.getType()!=Set.class
								&&!f.getType().toString().contains(".entity")
								&&!f.getName().equals("handler")
								&&!f.getName().equals("_filter_signature")
								&&!f.getName().equals("_methods_")){
							f.setAccessible(true);
							if(f.get(po)!=null){
								return true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LogUtil.traceError(log, e);
		}
		return false;
	}
	
	/**
	 * Ajax 分页参数
	 * @param url
	 * @param page
	 * @return
	 */
	public JSONObject getAjaxPageParam(String url,PageQueryResult<?> page){
		JSONObject pageJson = new JSONObject();
		pageJson.put("func", url);
		JSONArray ja = po2JsonList(page.getPageList(), false);
		pageJson.put("pageList", ja);
		pageJson.put("itemTotal", page.getItemTotal());
		pageJson.put("next", po2Json(page.getNext(),false));
		pageJson.put("prev", po2Json(page.getPrev(),false));
		pageJson.put("pageNo", page.getPageNo());
		pageJson.put("pageSize", page.getPageSize());
		pageJson.put("pageTotal", page.getPageTotal());
		return pageJson;
	}
	
}
