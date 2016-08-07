package com.michaeldowden.store.service;

import static com.michaeldowden.store.utils.OrderUtils.buildOrder;
import static com.michaeldowden.store.utils.OrderUtils.findOrderInHistory;
import static spark.Spark.halt;
import spark.Request;

import com.michaeldowden.store.model.Address;
import com.michaeldowden.store.model.Order;
import com.michaeldowden.store.model.ShoppingCart;

public class OrderService {
	private static final String ORDER = "ORDER";

	private void clearOrder(Request req) {
		req.session().invalidate();
	}

	public Order fetchOrder(Request req) {
		Object order = req.session().attribute(ORDER);
		if (order == null) {
			order = new Order();
			req.session().attribute(ORDER, order);
		}
		return (Order) order;
	}

	public void updateShippingAddress(Request req, Address address) {
		fetchOrder(req).setAddress(address);
	}

	public Address fetchShippingAddress(Request req) {
		return fetchOrder(req).getAddress();
	}

	public Integer checkout(Request req, ShoppingCart cart) {
		Integer orderNumber = buildOrder(fetchOrder(req), cart);
		// Clear current order
		clearOrder(req);
		// Return Order # for lookup
		return orderNumber;
	}

	public Order lookupOrder(Integer orderNumber) {
		Order order = findOrderInHistory(orderNumber);
		if (order == null) {
			halt(404, "Cannot find Order #" + orderNumber);
		}
		return order;
	}
}
