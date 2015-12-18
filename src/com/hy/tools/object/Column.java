package com.hy.tools.object;

/**
 * 字段对象
 * @author 云
 *
 */
public class Column {
	
	/** 字段名 */
	public String name;
	
	/** 字段注释 */
	public String notes;
	
	/** 字段类型 */
	public String type;
	
	/** 是否允许空 */
	public String isnull;
	
	/** 字段键值类型 */
	public String key;
	
	/** numeric长度 */
	public String precision;
	
	/** numeric精度 */
	public String scale;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsnull() {
		return isnull;
	}

	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

}
