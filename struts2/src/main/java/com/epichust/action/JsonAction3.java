package com.epichust.action;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.epichust.entity.Car;

public class JsonAction3
{
	private List<Car> car;

	public List<Car> getCar()
	{
		return car;
	}

	public void setCar(List<Car> car)
	{
		this.car = car;
	}

	public String getCarInfo()
	{
		car = new ArrayList<Car>();
		car.add(new Car(1, "宝马", new Date()));
		car.add(new Car(2, "奔驰", new Date()));
		car.add(new Car(3, "奥迪", new Date()));
		return "success";
	}

}
