package com.michaeldowden.store.utils;

import java.util.HashMap;
import java.util.Map;

import com.michaeldowden.store.model.Order;
import com.michaeldowden.store.model.ShoppingCart;

public final class OrderUtils {
	private static final Map<Integer, Order> ORDER_HISTORY = new HashMap<Integer, Order>();

	private static int lastOrderNumber = 1000000;

	private OrderUtils() {
	}

	public static Integer buildOrder(Order order, ShoppingCart cart) {
		Integer orderNumber = lastOrderNumber++;
		order.setOrderNumber(orderNumber);
		order.setItems(cart.getItems());
		order.setTotal(cart.getTotal());

		ORDER_HISTORY.put(orderNumber, order);

		return orderNumber;
	}

	public static Order findOrderInHistory(Integer orderNumber) {
		return ORDER_HISTORY.get(orderNumber);
	}

}
