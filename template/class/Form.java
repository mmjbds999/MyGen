package ${packageName}.forms;

import com.linzi.framework.annotation.ABuildWhereFieldName;
<#if searchList??>
<#list searchList as s>
import com.linzi.framework.annotation.ABuildWhereOptStr;
<#break>
</#list>
<#list searchList as s>
<#if s.typeStr=="Date">
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

import com.linzi.framework.web.PageDateRangeForm;

/**
 * <pre>
 * 生成的Form-by-${cPage}
 * </pre>
 * 
 * @author 黄云
 */
public class ${cPage}Form extends PageDateRangeForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	<#if searchList??>
	<#list searchList as s>
	<#if s.typeStr=="Date">
	private String ${s.name}_s;
	private String ${s.name}_e;
	<#elseif s.voField!="">
	private String ${s.name}_${s.voField};
	<#else>
	private ${s.typeStr} ${s.name};
	</#if>
	</#list>
	</#if>
	<#if addList??>
	<#list addList as add>
	<#if add.type?contains(".entity.")>
	private Integer ${add.name}_id;
	</#if>
	</#list>
	</#if>
	<#if isAdmin??>
	private String password;
	private String remember;
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
	<#elseif s.voField!="">
	@ABuildWhereOptStr(optStr="like")
	@ABuildWhereFieldName(name="${s.name}.${s.voField}")
	public String get${s.nameB}_${s.voField}() {
		return ${s.name}_${s.voField};
	}

	public void set${s.nameB}_${s.voField}(String ${s.name}_${s.voField}) {
		this.${s.name}_${s.voField} = ${s.name}_${s.voField};
	}
	<#else>
	<#if s.typeStr=='String'>
	@ABuildWhereOptStr(optStr="like")
	</#if>
	public ${s.typeStr} get${s.nameB}() {
		<#if s.showType=='select'>
		if(${s.name}!=null&&${s.name}==-1){
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
	
	<#if addList??>
	<#list addList as add>
	<#if add.type?contains(".entity.")>
	@ABuildWhereFieldName(name="bm_${add.name}.id")
	public Integer get${add.nameB}_id() {
		return ${add.name}_id;
	}

	public void set${add.nameB}_id(Integer ${add.name}_id) {
		this.${add.name}_id = ${add.name}_id;
	}
	</#if>
	</#list>
	</#if>
	
	<#if isAdmin??>
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemember() {
		return remember;
	}

	public void setRemember(String remember) {
		this.remember = remember;
	}
	</#if>
}
