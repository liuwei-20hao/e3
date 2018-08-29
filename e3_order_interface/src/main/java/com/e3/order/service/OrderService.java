package com.e3.order.service;

import com.e3.order.pojo.OrderInfo;
import com.e3.pojo.E3Result;

public interface OrderService {

	E3Result createOrder(OrderInfo orderInfo);
}
