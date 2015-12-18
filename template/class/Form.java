package ${packageName}.forms;

<#if searchList??>
<#list searchList as s>
<#if s.typeStr=="Date">
import com.linzi.framework.annotation.ABuildWhereOptStr;
import com.linzi.framework.annotation.ABuildWhereTimeField;
<#break>
</#if>
</#list>
<#list searchList as s>
<#if s.typeName?index_of("com.linzi.app.appserver.rmi.enums")!=-1>
import com.linzi.app.appserver.rmi.enums.${s.typeStr};
<#break>
</#if>
</#list>
</#if>

import java.io.Serializable;

import com.linzi.framework.annotation.ABuildWhereOptStr;
import com.linzi.framework.web.PageDateRangeForm;

/**
 * <pre>
 * 生成的Form-by-${cPage}
 * </pre>
 * 
 * @author 黄云
 * ${nowDate}
 */
public class ${cPage}Form extends PageDateRangeForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	<#if searchList??>
	<#list searchList as s>
	<#if s.typeStr=="Date">
	private String ${s.name}_s;
	private String ${s.name}_e;
	<#else>
	private ${s.typeStr} ${s.name};
	</#if>
	</#list>
	</#if>
	
	<#if searchList??>
	<#list searchList as s>
	<#if s.typeStr=="Date">
	@ABuildWhereTimeField(filed="${s.name}")
	public String get${s.nameB}_s() {
		return ${s.name}_s;
	}

	public void set${s.nameB}_s(String ${s.name}_s) {
		this.${s.name}_s = ${s.name}_s;
	}
	
	@ABuildWhereTimeField(isBegin=false, filed="${s.name}")
	public String get${s.nameB}_e() {
		return ${s.name}_e;
	}

	public void set${s.nameB}_e(String ${s.name}_e) {
		this.${s.name}_e = ${s.name}_e;
	}
	<#else>
	<#if s.typeStr=='String'>
	@ABuildWhereOptStr(optStr="like")
	</#if>
	public ${s.typeStr} get${s.nameB}() {
		<#if s.showType=='select'>
		if(${s.name}==-1){
			return null;
		}
		</#if>
		return ${s.name};
	}

	public void set${s.nameB}(${s.typeStr} ${s.name}) {
		this.${s.name} = ${s.name};
	}
	</#if>
	</#list>
	</#if>
	
}
