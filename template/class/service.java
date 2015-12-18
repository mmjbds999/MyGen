package ${packageName}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linzi.framework.db.PageQueryResult;
import com.linzi.framework.utils.LogUtil;
import com.linzi.framework.db.WhereBuilder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ${packageName}.dao.${classNameB}Dao;
import ${packageName}.entity.${classNameB};
import ${packageName}.entity.User;
import ${packageName}.forms.${classNameB}Form;

/**
 * <pre>
 * 啦--${classNameB}Service
 * </pre>
 * 
 * @author 黄云
 * ${nowDate}
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
		if (log.isDebugEnabled()) {
			log.debug(LogUtil.format("sql:%s", hql.toString()));
		}
		return ${className}Dao.findInPage(hql.toString(),build.getParams(), form);
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
