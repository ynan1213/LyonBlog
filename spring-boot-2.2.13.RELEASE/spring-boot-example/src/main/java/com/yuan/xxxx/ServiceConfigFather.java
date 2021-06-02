package com.epichust.xxxx;

public class ServiceConfigFather
{
	protected AService getA()
	{
		BService b = getB();
		return new AService();
	}

	protected BService getB()
	{
		return new BService();
	}
}
