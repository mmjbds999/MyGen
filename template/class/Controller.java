package ${packageName}.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ${packageNameForm}.forms.${cPage}Form;
import ${packageNameForm}.service.${cPage}Service;
import ${packageNameForm}.entity.${cPage};
import ${packageNameForm}.vo.${cPage}Vo;
<#if addList??>
<#list addList as s>
	<#if (s.saveType=="select" || s.saveType=="radio" || s.saveType=="checkbox")>
import ${packageName}.enums.${s.enumName};
	</#if>
	<#if (s.saveType=="selectvo" || s.saveType=="checkboxvo")>
import ${packageName}.entity.${s.voTableB};
import ${packageName}.service.${s.voTableB}Service;
	</#if>
    <#if s.isHidden && s.typeStr=="Date">
import java.util.Date;
    </#if>
</#list>
</#if>


import com.hy.tools.uitl.ChineseUtil;
import com.hy.tools.uitl.PoToJson;
import com.hy.tools.uitl.StringUtil;
import com.linzi.framework.db.PageQueryResult;
import com.linzi.framework.utils.StringUtils;
import com.linzi.framework.web.BinderUtil;
import com.linzi.framework.utils.EncryptUtil;

/**
 * 
 * <pre>
 * ${cPage}Controller--通用生成
 * </pre>
 * 
 * @author 黄云
 */
@Controller
@RequestMapping("/${pageName}")
public class ${cPage}Controller extends BaseAction {

    private static Logger logger = Logger.getLogger(${cPage}Controller.class);
    
    @Autowired 
    private ${cPage}Service ${pageName}Service;
    
    <#if addList??>
	<#list addList as s>
		<#if (s.saveType=="selectvo" || s.saveType=="checkboxvo") && s.voTable!=pageName>
	@Autowired
	private ${s.voTableB}Service ${s.voTable}Service;
		</#if>
	</#list>
	</#if>

    /**
     * 获取列表数据
     */
    @RequestMapping("/list.do")
    public ModelAndView list(String parentName, String parentComm) {
    	${cPage}Form form = BinderUtil.bindForm(request, ${cPage}Form.class, true);
    	ModelAndView mav = new ModelAndView("${pageName}");
    	<#if addList??>
    	<#list addList as s>
        <#if s.isSetDefault>
        if(getUser().get${s.nameB}()!=null&&getUser().get${s.nameB}().getId()!=null){
    		form.set${s.nameB}_id(getUser().get${s.nameB}().getId());
    		mav.addObject("${s.name}_id", getUser().get${s.nameB}().getId());
    	}else{
    		form.set${s.nameB}_id(-1);
    		mav.addObject("${s.name}_id", "");
    	}
        </#if>
        </#list>
    	</#if>
        PageQueryResult<${cPage}> page = ${pageName}Service.findByPage(form);
        page.setActionUrl("${pageName}/list.do");
        mav.addObject("parentName", parentName);
        mav.addObject("parentComm", ChineseUtil.toChinese(parentComm));
        mav.addObject("page", page);
		mav.addObject("form", form);
		if (page != null) {
			mav.addObject("page", page);
			${cPage}Vo ${pageName}Vo = new ${cPage}Vo();
			mav.addObject("${pageName}List", setVoList(${pageName}Vo.getORMList(page.getList())));
		}
        return mav;
    }
    
    /**
     * 根据PO查询--所有字段都可做查询条件
     */
    @RequestMapping("/query.do")
    @ResponseBody
    public String query(${cPage} ${pageName}) {
        return ${pageName}Service.findByPO(${pageName}).toJSONString();
    }
    
    /**
     * 将要数据库字段翻译为需要显示的信息
     * @param list
     * @return
     */
    protected JSONArray setVoList(List<${cPage}Vo> list){
    	JSONArray ja = new JSONArray();
    	if(list!=null&&list.size()>0){
    		for (${cPage}Vo ${pageName}Vo : list) {
    			ja.add(setVo(${pageName}Vo));
    		}
    	}
    	return ja;
    }
    
    /**
     * 将要数据库字段翻译为需要显示的信息
     * @param list
     * @return
     */
    protected JSONObject setVo(Object obj){
    	JSONObject jo = new JSONObject();
    	if(obj instanceof JSONObject){
    		jo = (JSONObject)obj;
    	}else{
    		jo = PoToJson.getInstance().po2Json(obj, true);
    	}
		<#if addList??>
		<#list addList as s>
			<#if (s.typeStr=="Date")>
		//翻译日期--格式为:yyyy-MM-dd
		jo.put("${s.name}", jo.getString("${s.name}").substring(0, 10));
			</#if>
			<#if (s.saveType=="select" || s.saveType=="radio")>
		//翻译枚举
		jo.put("${s.name}_vo", ${s.enumName}.parse(jo.getIntValue("${s.name}")).getName());
			</#if>
			<#if (s.saveType=="checkbox")>
		//翻译枚举
		String ckenum = jo.getString("${s.name}");
		String ckenum_vo = "";
		if(StringUtils.isNotEmpty(ckenum)){
			String[] ckes = ckenum.split(",");
			for (String c : ckes) {
				ckenum_vo += ${s.enumName}.parse(Integer.parseInt(c)).getName()+",";
			}
			if(ckenum_vo.length()>0){
				ckenum_vo = ckenum_vo.substring(0,ckenum_vo.length()-1);
			}
		}
		jo.put("${s.name}_vo", ckenum_vo);
			</#if>
			<#if (s.saveType=="selectvo")>
		//翻译关联表
		JSONObject ${s.voTable}Json = (JSONObject)jo.get("${s.voTable}");
	    jo.put("${s.name}_vo", ${s.voTable}Json.get("${s.voFieldName}"));
			</#if>
			<#if (s.saveType=="checkboxvo")>
		//翻译关联表
		String ck = jo.getString("${s.name}");
		String ck_vo = "";
		if(StringUtils.isNotEmpty(ck)){
			String[] cks = ck.split(",");
			for (String c : cks) {
				${s.voTableB} ${s.voTable} = ${s.voTable}Service.findByIdPO(Integer.parseInt(c));
				if(${s.voTable}!=null){
					ck_vo += ${s.voTable}.get${s.voFieldNameB}()+",";
				}
			}
			if(ck_vo.length()>0){
				ck_vo = ck_vo.substring(0,ck_vo.length()-1);
			}
		}
	    jo.put("${s.name}_vo", ck_vo);
			</#if>
		</#list>
		</#if>
    	return jo;
    }
    
    /**
     * 保存
     * @param param
     * @return
     */
    protected void savePo(${cPage} param<#if addList??><#list addList as s><#if s.saveType=="img" || s.saveType=="file">, MultipartFile ${s.name}_file</#if></#list></#if>, HttpServletResponse resp) {
    	<#if addList??>
		<#list addList as s>
    	<#if s.saveType=="img" || s.saveType=="file" || s.saveType=="pwd">
    	<#if s.saveType=="img" || s.saveType=="file">
        String ${s.name}_path = fileUpload(${s.name}_file, resp);
        </#if>
    	if(param.getId()!=null&&param.getId()>0){
    		${cPage} po = ${pageName}Service.findByIdPO(param.getId());
    			<#if s.saveType=="img" || s.saveType=="file">
        	if(StringUtil.isEmpty(${s.name}_path)){
        		${s.name}_path = po.get${s.nameB}();
        	}
        		<#elseif s.saveType=="pwd">
    		if(param.get${s.nameB}().equals("!@#$%^")){
        		String ${s.name} = po.get${s.nameB}();
    			param.set${s.nameB}(${s.name});
        	}else{
        		param.set${s.nameB}(EncryptUtil.md5(param.get${s.nameB}()));
        	}
        		</#if>
    	}else{
    		param.set${s.nameB}(EncryptUtil.md5(param.get${s.nameB}()));
    	}
    	<#if s.saveType=="img" || s.saveType=="file">
    	param.set${s.nameB}(${s.name}_path);
        </#if>
    	</#if>
    	<#if s.saveType=="selectvo">
        if(param.get${s.nameB}()!=null&&param.get${s.nameB}().getId()!=null){
        	${s.nameB} ${s.name} = ${s.name}Service.findByIdPO(param.get${s.nameB}().getId());
    		param.set${s.nameB}(${s.name});
    	}
        </#if>
        </#list>
        </#if>

        <#if addList??>
		<#list addList as s>
        <#if s.typeName?index_of(".entity.")!=-1>
        if(param.get${s.nameB}()==null || param.get${s.nameB}().getId()==null){
    		param.set${s.nameB}(null);
    	}
        </#if>
        <#if s.isHidden && s.typeStr=="Date">
		param.set${s.nameB}(new Date());
        </#if>
        </#list>
        </#if>
        ${pageName}Service.save(param);
    }
    
    /**
     * 获取列表数据--all
     */
    @RequestMapping("/all.do")
    @ResponseBody
    public String all() {
    	return ${pageName}Service.findAll().toJSONString();
    }
    
    /**
     * 查看
     * @param id
     * @return
     */
    @RequestMapping("/view.do")
    @ResponseBody
    public String view(Integer id) {
        return setVo(${pageName}Service.findById(id)).toString();
    }
    
    /**
     * 保存
     * @param param
     * @return
     */
    @RequestMapping("/save.do")
    public String save(${cPage} param<#if addList??><#list addList as s><#if s.saveType=="img" || s.saveType=="file">, MultipartFile ${s.name}_file</#if></#list></#if>, HttpServletResponse resp) {
    	savePo(param<#if addList??><#list addList as s><#if s.saveType=="img" || s.saveType=="file">, ${s.name}_file</#if></#list></#if>, resp);
        return "redirect:/${pageName}/list.do";
    }
    
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete.do")
    @ResponseBody
    public String delete(Integer id){
    	return ${pageName}Service.del(id).toJSONString();
    }
    
    /**
     * 用于状态变更的功能--例如【审核-上架】之类的：暂时不维护审核历史
     * @param id
     * @param option
     * @param isAudit
     * @return
     */
    @RequestMapping("/changeStatus.do")
    @ResponseBody
    public String changeStatus(Integer id, String option<#if addList??><#list addList as add><#if add.optionName!="">, ${add.typeStr} ${add.name}</#if></#list></#if>){
    	${cPage} ${pageName} = ${pageName}Service.findByIdPO(id);
        <#if addList??>
	  	<#list addList as add>
			<#if add.optionName!="">
        if(option.equals("${add.name}")){
        	${pageName}.set${add.nameB}(${add.name}==0?1:0);
        }
        	</#if>
    	</#list>
    	</#if>
    	return ${pageName}Service.save(${pageName}).toJSONString();
    }
    
}
