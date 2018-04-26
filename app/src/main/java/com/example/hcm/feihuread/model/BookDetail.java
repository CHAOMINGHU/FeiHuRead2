package com.example.hcm.feihuread.model;

public class BookDetail
{
	String title;    //标题
	String author;   //作者
	String intro;    //简介
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
	public String getIntro()
	{
		return intro;
	}
	public void setIntro(String intro)
	{
		this.intro = intro;
	}
	public BookDetail(String title, String author, String intro)
	{
		super();
		this.title = title;
		this.author = author;
		this.intro = intro;
	}
	
}
