package com.lizhihao.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lizhihao.cms.entity.Special;

/**
 * @author LZH
 * @Date 2019年10月25日
 * 
 */

public interface SpecialMapper {
	
	// 获取专题的列表
	@Select("SELECT * FROM cms_special ORDER BY id DESC")
	public List<Special> getSpeList();
	
	// 添加专题
	@Insert("INSERT INTO cms_special (title,digest,created) VALUES (#{title},#{digest},now())")
	public int addSpecial(Special special);
	
	// 根据ID获取专题
	@Select("SELECT * FROM cms_special WHERE id = #{value}")
	public Special getSpeById(Integer id);
	
	// 在专题内添加文章
	@Insert("INSERT INTO cms_special_article (sid,aid) VALUES (#{sid},#{aid})")
	public int addSpeArt(@Param("sid")Integer speId, @Param("aid")Integer artId);
	
	// 删除中间表数据
	@Delete("DELETE FROM cms_special_article WHERE sid = #{sid} AND aid = #{aid}")
	public int delSpeArt(@Param("sid")Integer speId, @Param("aid")Integer artId);
	
	// 计算专题数量
	@Select("SELECT COUNT(sid) FROM cms_special_article WHERE sid = #{value}")
	public int countArtNum(Integer id);
	
	// 修改专题
	@Update("UPDATE cms_special SET title=#{title},digest=#{digest} WHERE id=#{id}")
	public int modify(Special special);
	
}

