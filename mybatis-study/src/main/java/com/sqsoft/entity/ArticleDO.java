package com.sqsoft.entity;

import java.io.Serializable;
import java.util.Date;

import com.sqsoft.typeHandler.ArticleTypeEnum;

public class ArticleDO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String title;
	
	private ArticleTypeEnum type;
	
	private AuthorDO author;
	
	private String content;
	
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArticleTypeEnum getType() {
		return type;
	}

	public void setType(ArticleTypeEnum type) {
		this.type = type;
	}

	public AuthorDO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDO author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ArticleDO [id=" + id + ", title=" + title + ", type=" + type + ", author=" + author + ", content="
				+ content + ", createTime=" + createTime + "]";
	}
	
}
