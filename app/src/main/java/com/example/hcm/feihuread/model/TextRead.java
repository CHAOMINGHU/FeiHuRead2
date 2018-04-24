package com.example.hcm.feihuread.model;

public class TextRead
{
	String textContent;
    String title;
    int textChapter;
    int CountChapter;
	public String getTextContent()
	{
		return textContent;
	}
	public void setTextContent(String textContent)
	{
		this.textContent = textContent;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public int getTextChapter()
	{
		return textChapter;
	}
	public void setTextChapter(int textChapter)
	{
		this.textChapter = textChapter;
	}
	public int getCountChapter()
	{
		return CountChapter;
	}
	public void setCountChapter(int countChapter)
	{
		CountChapter = countChapter;
	}
	public TextRead(String textContent, String title, int textChapter,
			int countChapter)
	{
		super();
		this.textContent = textContent;
		this.title = title;
		this.textChapter = textChapter;
		CountChapter = countChapter;
	}
	public TextRead()
	{
		// TODO Auto-generated constructor stub
	}
	
	
}
