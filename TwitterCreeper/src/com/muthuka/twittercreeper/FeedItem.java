package com.muthuka.twittercreeper;

public class FeedItem {
	private String author = "";
	private String message = "";
	private String timeTweeted = "";
	
	public String getAuthor()
	{
		return(author);
	}
	
	public void setAuthor(String local)
	{
		author = local;
	}
	
	public String getMessage()
	{
		return(message);
	}
	
	public void setMessage(String local)
	{
		message = local;
	}

	public String getTimeTweeted()
	{
		return(timeTweeted);
	}
	
	public void setTimeTweeted(String local)
	{
		timeTweeted = local;
	}
}
