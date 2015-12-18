package com.<package>.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

<other_import>

/** <entity_notes> */
@Entity
@Table(name = "<database>.<table_r>")
public class <table> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	<COL>
	/** <column_notes> */<COL_ID>
	@Id
	<generator></COL_ID>
	@Column(<column_attr>)
	private <column_type> <column_name>;
	</COL>
	
	<COL>
	public <column_type> get<column_name_b>() {
		return this.<column_name>;
	}

	public void set<column_name_b>(<column_type> <column_name>) {
		this.<column_name> = <column_name>;
	}
	</COL>

}
