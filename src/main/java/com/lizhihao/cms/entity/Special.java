package com.lizhihao.cms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author LZH
 * @Date 2019年10月25日 专题实体类
 */

public class Special implements Serializable {

	private static final long serialVersionUID = 8237665390593606845L;

	private Integer id;
	private String title;
	private String digest;
	private Date created;

	private Integer articleNum; // 该专题文章数量

	private List<Article> articleList;

	public Special() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Special(Integer id, String title, String digest, Date created, List<Article> articleList) {
		super();
		this.id = id;
		this.title = title;
		this.digest = digest;
		this.created = created;
		this.articleList = articleList;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the digest
	 */
	public String getDigest() {
		return digest;
	}

	/**
	 * @param digest the digest to set
	 */
	public void setDigest(String digest) {
		this.digest = digest;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the articleList
	 */
	public List<Article> getArticleList() {
		return articleList;
	}

	/**
	 * @param articleList the articleList to set
	 */
	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	/**
	 * @return the articleNum
	 */
	public Integer getArticleNum() {
		return articleNum;
	}

	/**
	 * @param articleNum the articleNum to set
	 */
	public void setArticleNum(Integer articleNum) {
		this.articleNum = articleNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Special [id=" + id + ", title=" + title + ", digest=" + digest + ", created=" + created
				+ ", articleNum=" + articleNum + ", articleList=" + articleList + "]";
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
		Special other = (Special) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
