package com.lzh.cms.entity;

import java.io.Serializable;

/**
 * @author LZH
 * @Date 2019年10月23日
 * 封装Tag标签类
 */

public class Tag implements Serializable {

	private static final long serialVersionUID = 4366908121972794907L;

	private Integer id;
	private String tagname;

	
	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 有参构造
	public Tag(String tagname) {
		super();
		this.tagname = tagname;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the tagname
	 */
	public String getTagname() {
		return tagname;
	}

	/**
	 * @param tagname the tagname to set
	 */
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tag [id=" + id + ", tagname=" + tagname + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
