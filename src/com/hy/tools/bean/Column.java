package com.hy.tools.bean;

import java.util.List;

/**
 * <pre>
 * 字段bean
 * </pre>
 * 
 * @author 黄云
 * 2015年11月11日
 */
public class Column {

	/** 通用 */
	private String name;//字段名
	private String comment;//注释
	private Class<?> type;//类型
	private int length;//长度
	private String typeStr;
	private String nameB;
	private String typeName;
	private String optionName;
	
	/** one2many */
	private String qname;//many端查询字段
	private boolean userbtn;//是否需要关联按钮
	
	/** valid相关 */
	private List<String> valids;//需要验证的东东
	private String param;//需要验证的东东--暂时只为eq提供参数，其他需要的话再说
	
	/** 列表相关 */
	private String showType;//页面展示类型
	private String voField;//页面展示类型
	private List<Integer> codes;//下拉选项code数组
	private List<String> names;//下拉选项name数组
	
	/** 添加相关 */
	private String saveType;//保存页面展示类型
	private List<Integer> saveCodes;//下拉选项code数组
	private List<String> saveNames;//下拉选项name数组
	private List<String> saveColors;//下拉选项color数组
	private String voName;//要做查询的voName--如：AppTypeResponse
	private String voTable;//要做查询的voName--如：appType
	private String voTableB;//要做查询的voName--如：AppType
	private String voFieldName;//要做查询的voFieldName
	private String voFieldNameB;//要做查询的voFieldName-首字母大写的
	private String defaultVal;//默认值
	private String enumName;//使用的枚举的Name
	private Boolean isHidden=false;
	
	/** 查看详情相关 */
	private String viweType;//保存页面展示类型
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public List<Integer> getCodes() {
		return codes;
	}
	public void setCodes(List<Integer> codes) {
		this.codes = codes;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public List<Integer> getSaveCodes() {
		return saveCodes;
	}
	public void setSaveCodes(List<Integer> saveCodes) {
		this.saveCodes = saveCodes;
	}
	public List<String> getSaveNames() {
		return saveNames;
	}
	public void setSaveNames(List<String> saveNames) {
		this.saveNames = saveNames;
	}
	public String getViweType() {
		return viweType;
	}
	public void setViweType(String viweType) {
		this.viweType = viweType;
	}
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	public String getNameB() {
		return nameB;
	}
	public void setNameB(String nameB) {
		this.nameB = nameB;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getVoName() {
		return voName;
	}
	public void setVoName(String voName) {
		this.voName = voName;
	}
	public String getVoFieldName() {
		return voFieldName;
	}
	public void setVoFieldName(String voFieldName) {
		this.voFieldName = voFieldName;
	}
	public List<String> getSaveColors() {
		return saveColors;
	}
	public void setSaveColors(List<String> saveColors) {
		this.saveColors = saveColors;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public String getVoTable() {
		return voTable;
	}
	public void setVoTable(String voTable) {
		this.voTable = voTable;
	}
	public String getVoTableB() {
		return voTableB;
	}
	public void setVoTableB(String voTableB) {
		this.voTableB = voTableB;
	}
	public String getVoFieldNameB() {
		return voFieldNameB;
	}
	public void setVoFieldNameB(String voFieldNameB) {
		this.voFieldNameB = voFieldNameB;
	}
	public Boolean getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public List<String> getValids() {
		return valids;
	}
	public void setValids(List<String> valids) {
		this.valids = valids;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getVoField() {
		return voField;
	}
	public void setVoField(String voField) {
		this.voField = voField;
	}
	public String getQname() {
		return qname;
	}
	public void setQname(String qname) {
		this.qname = qname;
	}
	public boolean isUserbtn() {
		return userbtn;
	}
	public void setUserbtn(boolean userbtn) {
		this.userbtn = userbtn;
	}
	
}
