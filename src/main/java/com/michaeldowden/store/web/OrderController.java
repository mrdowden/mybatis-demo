package com.michaeldowden.store.web;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;
import com.michaeldowden.store.model.Address;
import com.michaeldowden.store.service.CartService;
import com.michaeldowden.store.service.OrderService;

public class OrderController {
	private final Gson gson = new Gson();
	private final CartService cartSvc = new CartService();
	private final OrderService orderSvc = new OrderService();

	public void initialize() {
		get("/svc/order/shipping", "application/json", (req, res) -> {
			return orderSvc.fetchShippingAddress(req);
		}, gson::toJson);

		put("/svc/order/shipping", (req, res) -> {
			Address address = gson.fromJson(req.body(), Address.class);

			orderSvc.updateShippingAddress(req, address);
			return true;
		});

		post("/svc/order/checkout", (req, res) -> {
			return orderSvc.checkout(req, cartSvc.fetchCart(req));
		});

		get("/svc/order/:orderNumber", "application/json", (req, res) -> {
			Integer orderNumber = Integer.valueOf(req.params("orderNumber"));

			return orderSvc.lookupOrder(orderNumber);
		}, gson::toJson);
	}
}
