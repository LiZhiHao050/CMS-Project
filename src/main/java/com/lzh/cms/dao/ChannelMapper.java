package com.lzh.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.lzh.cms.entity.Channel;

/**
 * @author LZH
 * @Date 2019年10月17日
 * 
 */

public interface ChannelMapper {
	
	/**
	 * 获取频道列表
	 * @return
	 */
	@Select("SELECT * FROM cms_channel ORDER BY id")
	public List<Channel> getAllChannel();

}

