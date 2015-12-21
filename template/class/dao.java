package ${packageName}.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ${packageName}.entity.${tableName};

/**
 * <pre>
 * ${tableComment}DAO
 * </pre>
 * 
 * @author 黄云
 * ${now}
 */
@Repository
public class ${tableName_R}Dao extends BaseMainDao<${tableName}, Integer>{
	
	<#if isAdmin??>
	/**
	 * 登录方法
	 * @param account
	 * @param password
	 * @return
	 */
	public Admin findByUserNameAndPassword(String account, String password){
		List<Admin> list = this.find("from Admin a where a.account=? and a.password=?", account, password);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	</#if>
	
}