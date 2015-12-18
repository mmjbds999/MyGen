package ${package};

import java.io.Serializable;

import com.hy.tools.annotaion.AListObj;
import com.hy.tools.annotaion.AValid;
import com.hy.tools.annotaion.AModelName;
import com.linzi.framework.db.VoForPo;
<#if voList??>
<#list voList as vo>
<#if vo.name!="id"&&(vo.length==50 || vo.typeStr=="Date")>
import com.hy.tools.annotaion.ASearchObj;
<#break>
</#if>
</#list>
</#if>
<#if voList??>
<#list voList as vo>
<#if vo.name!="id"&&vo.typeStr=="Date">
import java.util.Date;
<#break>
</#if>
</#list>
</#if>
<#if voList??>
<#list voList as vo>
<#if vo.name!="id"&&vo.saveType!="">
import com.hy.tools.enums.SaveInputType;
<#break>
</#if>
</#list>
</#if>
<#if voList??>
<#list voList as vo>
<#if vo.name!="id"&&vo.typeStr=="Date">
import com.hy.tools.enums.SearchInputType;
<#break>
</#if>
</#list>
</#if>
<#if voList??>
<#list voList as vo>
<#if vo.name!="id"&&vo.valids?size!=0>
import com.hy.tools.enums.ValidEnum;
<#break>
</#if>
</#list>
</#if>
<#if voList??>
<#list voList as vo>
<#if vo.name!="id"&&vo.length==9>
import ${packageName}.entity.${vo.voName};
</#if>
</#list>
</#if>

/**
 * <pre>
 * ${className}Vo--还有个功能是生成界面用哦
 * </pre>
 * 
 * @author 黄云
 * ${nowDate}
 */
@AModelName(modelName = "${comment}", pageName = "${classNameL}")
public class ${className}Vo extends VoForPo<${className}Vo> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	<#if voList??>
	<#list voList as vo>
	<#if vo.name!="id">
	@AValid<#if vo.valids?size!=0>(valids={<#list vo.valids as vld><#if vld_index!=0>,</#if>ValidEnum.${vld}</#list>})</#if>
	<#if vo.length==50 || vo.typeStr=="Date">
	@ASearchObj<#if vo.typeStr=="Date">(type = SearchInputType.${vo.saveType})</#if>
	</#if>
	@AListObj(comment = "${vo.comment}", length = ${vo.length}, <#if vo.saveType!="">cType = SaveInputType.${vo.saveType},</#if> option = "AU<#if vo.length<=50>S</#if>V")
	</#if>
	private ${vo.typeStr} ${vo.name};
	
	</#list>
	</#if>
	
	<#if voList??>
	<#list voList as vo>
	public ${vo.typeStr} get${vo.nameB}() {
		return this.${vo.name};
	}

	public void set${vo.nameB}(${vo.typeStr} ${vo.name}) {
		this.${vo.name} = ${vo.name};
	}
	
	</#list>
	</#if>
	
}
