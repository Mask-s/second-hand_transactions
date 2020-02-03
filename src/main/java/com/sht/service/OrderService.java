package com.sht.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sht.entity.Orders;

public interface OrderService {
	List<Orders> queryOrders(Orders orders);
	int createOrder(Orders orders);
	int modifyOrder(Orders orders);
	int deleteOrder(int orderId);
}
