package com.lzh.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzh.cms.dao.ChannelMapper;
import com.lzh.cms.entity.Channel;

/**
 * @author LZH
 * @Date 2019年10月17日
 * 频道分类服务层
 */

@Service
public interface ChannelService {
	
	// 获取频道列表
	List<Channel> getAllChannel();
	
}

