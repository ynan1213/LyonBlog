package com.epichust.expcetion;

public class UserException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public UserException()
	{
		super();
	}

	public UserException(String message)
	{
		super(message);
	}
}
