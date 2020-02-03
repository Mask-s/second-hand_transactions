package com.sht.dao;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import com.sht.entity.Orders;



public interface OrderDao {
	List<Orders> queryOrders(@Param("orders")Orders orders);
	int createOrder(@Param("orders")Orders orders);
	int modifyOrder(Orders orders);
	int deleteOrder(@Param("orderId")int orderId);
}
