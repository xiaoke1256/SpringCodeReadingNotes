package com.xiaoke1256.spring.mytest.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "TEST_TABLE")
public class TestTable {
	
	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "AA")
	private String aa;
	
	@Column(name = "BB")
	private String bb;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAa() {
		return aa;
	}

	public void setAa(String aa) {
		this.aa = aa;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}
	
	
}
