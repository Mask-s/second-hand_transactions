package com.sht.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sht.BaseTest;
import com.sht.entity.Orders;
import com.sht.entity.Person;

public class PersonDaoTest extends BaseTest{
	@Autowired
	private PersonDao persondao;
	@Autowired
	private OrderDao orderDao;
	@Test
	public void getallperson() {
		/*List<Person> list = persondao.getAllPerson(1,10);
		assertEquals(1, list.size());*/
		Orders orders = new Orders();
		//orders.setAddress("aaa");
		orders.setOrderCreateTime("2020-01-15 14:58:52");
		List<Orders> orders2 = orderDao.queryOrders(orders);
		for(Orders o:orders2) {
			System.out.println(o.getOwner().getPhone());
			System.out.println(o.getOwner().getUserId());
		}
	}
}
