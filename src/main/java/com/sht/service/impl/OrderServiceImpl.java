package com.sht.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sht.dao.OrderDao;
import com.sht.entity.Orders;
import com.sht.exceptions.MyRuntimeException;
import com.sht.service.OrderService;
import com.sht.util.DateUtil;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderDao orderDao;
	
	@Override
	public List<Orders> queryOrders(Orders orders) {
		return orderDao.queryOrders(orders);
	}

	@Override
	@Transactional
	public int createOrder(Orders orders) throws MyRuntimeException{
		//count -1:传入空对象  0：插入失败  1：成功 -2:内部错误
		int count = -2;
		int pk;
		if(orders == null) {
			return -1;
		}else {
			orders.setOrderCreateTime(DateUtil.dateToString());
			orders.setOrderStatus(0);
			orders.setShopStatus(0);
			int hashCodeV = UUID.randomUUID().toString().hashCode();
	        if(hashCodeV < 0) {//有可能是负数
	            hashCodeV = - hashCodeV;
	        }
	        orders.setOrderNumber(String.format("%015d", hashCodeV));
	        try {
	        	count = orderDao.createOrder(orders);
	        	//返回订单id
				pk = orders.getOrderId();
			} catch (Exception e) {
				throw new MyRuntimeException("添加失败");
			}
	        
	        if(count <= 0) {
	        	return 0;
	        }else {
	        	count = pk;
	        }
		}		
		return count;
	}

	@Transactional
	@Override
	public int modifyOrder(Orders orders) throws MyRuntimeException{
		//count -1:传入空对象  0：修改失败  1：成功 -2:内部错误
		int count = -2;
		if(orders == null) {
			return -1;
		}else {
			try {
				count = orderDao.modifyOrder(orders);
			} catch (Exception e) {
				throw new MyRuntimeException("更新失败"+e.getMessage());
			}			
			if(count <= 0) {
				count = 0;
			}
		}		
		return count;
	}

	@Override
	public int deleteOrder(int orderId) {
		//count -1:传入空对象  0：删除失败  1：成功 -2:内部错误
		int count = -2;
		if(orderId == -1) {
			count = -1;
		}else {
			count = orderDao.deleteOrder(orderId);
			if(count <= 0) {
				count = 0;
			}else {
				count = 1;
			}
		}
		return count;
	}

}
