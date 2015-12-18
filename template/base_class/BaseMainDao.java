package ${packageName}.dao;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.linzi.framework.db.BaseDao;

/**
 * <pre>
 * 使用主数据库的Dao的基类
 * </pre>
 * 
 * @author 梁韦江 2015年7月18日
 */
public class BaseMainDao<T, K extends Serializable> extends BaseDao<T, K> {

	@Resource(name = "mainHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	protected HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

}
