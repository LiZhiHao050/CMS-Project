package com.lizhihao.cms.entity;

import java.io.Serializable;

/**
 * @author LZH
 * @Date 2019年10月29日 投票实体类
 */

public class Votes implements Serializable {

	private static final long serialVersionUID = -8414132112468942649L;
	
	private String optionKey;
	private Integer voteNum;
	private String optionTitle;
	private Integer voteNumTotal;

	public Votes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Votes(String optionKey, Integer voteNum, String optionTitle, Integer voteNumTotal) {
		super();
		this.optionKey = optionKey;
		this.voteNum = voteNum;
		this.optionTitle = optionTitle;
		this.voteNumTotal = voteNumTotal;
	}

	/**
	 * @return the optionKey
	 */
	public String getOptionKey() {
		return optionKey;
	}

	/**
	 * @param optionKey the optionKey to set
	 */
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}

	/**
	 * @return the voteNum
	 */
	public Integer getVoteNum() {
		return voteNum;
	}

	/**
	 * @param voteNum the voteNum to set
	 */
	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}

	/**
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * @param optionTitle the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * @return the voteNumTotal
	 */
	public Integer getVoteNumTotal() {
		return voteNumTotal;
	}

	/**
	 * @param voteNumTotal the voteNumTotal to set
	 */
	public void setVoteNumTotal(Integer voteNumTotal) {
		this.voteNumTotal = voteNumTotal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Votes [optionKey=" + optionKey + ", voteNum=" + voteNum + ", optionTitle=" + optionTitle
				+ ", voteNumTotal=" + voteNumTotal + "]";
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
		result = prime * result + ((optionKey == null) ? 0 : optionKey.hashCode());
		result = prime * result + ((optionTitle == null) ? 0 : optionTitle.hashCode());
		result = prime * result + ((voteNum == null) ? 0 : voteNum.hashCode());
		result = prime * result + ((voteNumTotal == null) ? 0 : voteNumTotal.hashCode());
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
		Votes other = (Votes) obj;
		if (optionKey == null) {
			if (other.optionKey != null)
				return false;
		} else if (!optionKey.equals(other.optionKey))
			return false;
		if (optionTitle == null) {
			if (other.optionTitle != null)
				return false;
		} else if (!optionTitle.equals(other.optionTitle))
			return false;
		if (voteNum == null) {
			if (other.voteNum != null)
				return false;
		} else if (!voteNum.equals(other.voteNum))
			return false;
		if (voteNumTotal == null) {
			if (other.voteNumTotal != null)
				return false;
		} else if (!voteNumTotal.equals(other.voteNumTotal))
			return false;
		return true;
	}

}
