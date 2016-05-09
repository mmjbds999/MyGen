package ${packageName}.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linzi.framework.db.PageQueryResult;
import com.linzi.framework.utils.LogUtil;
import com.linzi.framework.utils.StringUtils;
import com.linzi.framework.db.WhereBuilder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ${packageName}.dao.${classNameB}Dao;
import ${packageName}.entity.${classNameB};
import ${packageName}.forms.${classNameB}Form;

/**
 * <pre>
 * 啦--${classNameB}Service
 * </pre>
 * 
 * @author 黄云
 */
@Service
@Transactional
public class ${classNameB}Service extends BaseServcie<${classNameB}>{
	
	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(${classNameB}Service.class);
	
	@Autowired
	private ${classNameB}Dao ${className}Dao;
	
	/**
	 * 分页查询--所有many-to-one的关联表都干出来
	 * @param form
	 * @return
	 */
	public PageQueryResult<${classNameB}> findByPage(${classNameB}Form form) {
		WhereBuilder build = form.buildWhereAndParams(null, null);
		StringBuffer hql=new StringBuffer("from ${classNameB} ${className}");
		<#if sqlList??>
		<#list sqlList as sql>
		hql.append("${sql}");
		</#list>
		</#if>
		hql.append(" where ").append(build.getWhereSql());
		hql.append(" order by ${className}.id desc");
		return ${className}Dao.findInPage(hql.toString(),build.getParams(), form);
	}
	
	/**
	 * 根据po查询，所有po字段都可作为查询条件
	 * @param id
	 * @return
	 */
	public JSONObject findByPO(${classNameB} ${className}){
		JSONObject jo = po2Json(${className}, true);
		jo = JSONObject.parseObject(jo.toString());
		if(jo!=null&&jo.size()>0){
			StringBuffer sql = new StringBuffer();
			sql.append("where 1=1 ");
			List<Object> params = new ArrayList<Object>();
			for (String key : jo.keySet()) {
				if(jo.get(key)!=null&&StringUtils.isNotEmpty(jo.get(key).toString())){
					if(jo.get(key) instanceof String){
						sql.append("and ").append(key).append(" like ? ");
						params.add(jo.get(key)+"%");
					}else if(jo.get(key) instanceof JSONObject){
						JSONObject json = (JSONObject)jo.get(key);
						if(json.get("id")==null || json.getInteger("id")==-1){
							sql.append("and ${className}.").append(key).append(" is null ");
							//TODO 类似这种情况的一般不用is null或is not null 而是加字段处理，否则会进行全表检索，导致索引失效，降低效率，这里是为了方便，所以不要计较了，哈哈哈！
						}else{
							sql.append("and ").append(key).append(".id=? ");
							params.add(json.get("id"));
						}
					}else{
						sql.append("and ").append(key).append("=? ");
						params.add(jo.get(key));
					}
				}
			}
			JSONArray ja = po2JsonList(${className}Dao.findInPage(String.format("from ${classNameB} %s", sql), params.toArray(), 1, 10).getList(),false);
			return result(true, "success", ja);
		}
		JSONArray ja = po2JsonList(${className}Dao.findInPage("from ${classNameB}", new Object[]{}, 1, 10).getList(),false);
		return result(true, "success", ja);
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public JSONArray findAll(){
		return po2JsonList(${className}Dao.getAll(), false);
	}
	
	/**
	 * 查询单条
	 * @param id
	 * @return
	 */
	public JSONObject findById(Integer id){
		return po2Json(${className}Dao.get(id), true);
	}
	
	/**
	 * 查询单条
	 * @param id
	 * @return
	 */
	public ${classNameB} findByIdPO(Integer id){
		return ${className}Dao.get(id);
	}
	
	/**
	 * 保存
	 * @param addr
	 */
	public JSONObject save(${classNameB} ${className}){
		try{
			${className}Dao.save(${className});
			return result(true, "保存成功！");
		}catch(Exception e){
			return result(true, "保存失败！");
		}
	}
	
	/**
	 * 删除ByID
	 * @param id
	 */
	public JSONObject del(Integer id){
		try{
			${className}Dao.removeByID(id);
			return result(true, "删除成功！");
		}catch(Exception e){
			return result(true, "删除失败！");
		}
	}
}
