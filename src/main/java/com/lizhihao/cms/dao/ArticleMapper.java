package com.lizhihao.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lizhihao.cms.entity.Article;
import com.lizhihao.cms.entity.Links;
import com.lizhihao.cms.entity.Tag;

/**
 * @author LZH
 * @Date 2019年10月17日
 * 	文章类
 */

public interface ArticleMapper {
	
	// 根据频道ID和分类ID获取文章列表
	List<Article> getArtList(@Param("chnId")Integer chnId, @Param("catId")Integer catId);

	// 获取文章内容
	Article getDetail(Integer aId);
	
	// 获取热门文章
	List<Article> getHotArticle();

	// 获取最新文章
	List<Article> getNewArticle(int num);
	
	// 获取友情链接
	@Select("SELECT id, title, url, created, updated FROM cms_links LIMIT #{value}")
	List<Links> getFriendLinks(int num);
	
	// 根据文章ID回显
	Article findArtById(Integer id);
	
	// 管理文章(管理员)
	List<Article> managerArts(@Param("status") Integer status);
	
	// 审核文章状态(管理员)
	@Update("UPDATE cms_article SET status = #{status}, updated = now() WHERE id = #{artId}")
	int auditStatus(@Param("status")Integer status, @Param("artId")Integer artId);
	
	// 修改文章热门状态(管理员)
	@Update("UPDATE cms_article SET hot = #{status}, updated = now() WHERE id = #{artId}")
	int setHot(@Param("status")Integer status, @Param("artId")Integer artId);
	
	// 查找标签(根据标签名称获取标签对象)
	@Select("SELECT * FROM cms_tag WHERE tagname = #{value} LIMIT 1")
	Tag getTag(String tag);
	
	
	// 创建标签
	int addTag(Tag resTag);
	
	// 添加文章和标签到中间表
	@Insert("INSERT INTO cms_article_tag_middle VALUES (#{artId},#{tagId})")
	void addArtsTag(@Param("artId")Integer artId, @Param("tagId")Integer tagId);
	
	// 修改文章删除中间表数据
	@Delete("DELETE FROM cms_article_tag_middle WHERE aid = #{value}")
	void delArtTags(Integer id);

	// 根据专题ID获取文章
	@Select("SELECT a.id, a.title, a.created FROM cms_article a JOIN cms_special_article sa "
			+ " ON a.id = sa.aid WHERE sa.sid = #{value}")
	List<Article> getArtBySpeId(Integer id);
	
	@Update("UPDATE cms_article SET hits = hits + 1 WHERE id = #{value}")
	int updateHits(Integer aId);
	
	// 获取所有数据
	List<Article> getAll();
	
	
}

