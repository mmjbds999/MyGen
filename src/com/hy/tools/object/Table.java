package com.hy.tools.object;

import java.util.ArrayList;
import java.util.List;

/**
 * 表对象
 * @author 云
 *
 */
public class Table {

	/** 表名 */
	public String name;
	
	/** 表注释 */
	public String notes;
	
	/** 一对多关系集合 */
	public List<String> one = new ArrayList<String>();
	
	/** 多对一关系集合 */
	public List<String> many = new ArrayList<String>();
	
	/** 表字段集合 */
	public List<Column> columns = new ArrayList<Column>();

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

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<String> getOne() {
		return one;
	}

	public void setOne(List<String> one) {
		this.one = one;
	}

	public List<String> getMany() {
		return many;
	}

	public void setMany(List<String> many) {
		this.many = many;
	}
	
}
