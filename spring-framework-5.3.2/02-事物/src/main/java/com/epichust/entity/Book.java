package com.epichust.entity;

/**
 * @author yuannan
 * @Description
 * @date: 2019年3月29日
 */
public class Book
{
	private int id;

	private String name;

	private int price;

	public Book()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(String name, int price)
	{
		super();
		this.name = name;
		this.price = price;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	@Override
	public String toString()
	{
		return "Book [id=" + id + ", name=" + name + ", price=" + price + "]";
	}


}
