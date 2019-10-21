package com.lzh.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzh.cms.dao.ChannelMapper;
import com.lzh.cms.entity.Channel;
import com.lzh.cms.service.ChannelService;

/**
 * @author LZH
 * @Date 2019年10月17日
 * 频道类服务实现层
 */

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	ChannelMapper cm;
	
	// 获取频道列表
	@Override
	public List<Channel> getAllChannel() {
		List<Channel> allChannel = cm.getAllChannel();
		return allChannel;
	}
	
}

