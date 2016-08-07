package com.michaeldowden.store.service;

import static spark.Spark.halt;
import spark.Request;

import com.michaeldowden.store.model.OrderItem;
import com.michaeldowden.store.model.ShoppingCart;
import com.michaeldowden.store.utils.CartUtils;
import com.michaeldowden.store.utils.ItemNotFoundException;

public class CartService {
	private static final String SHOPPING_CART = "SHOPPING_CART";

	public ShoppingCart fetchCart(Request req) {
		Object cart = req.session().attribute(SHOPPING_CART);
		if (cart == null) {
			cart = new ShoppingCart();
			req.session().attribute(SHOPPING_CART, cart);
		}
		return (ShoppingCart) cart;
	}

	public void addToCart(Request req, OrderItem newItem) {
		CartUtils.addToCart(fetchCart(req), newItem);
	}

	public void updateQuantity(Request req, Integer itemId, Integer qty) {
		try {
			CartUtils.updateQuantity(fetchCart(req), itemId, qty);
		} catch (ItemNotFoundException e) {
			halt(404, e.getLocalizedMessage());
		}
	}

	public void removeFromCart(Request req, Integer itemId) {
		try {
			CartUtils.removeFromCart(fetchCart(req), itemId);
		} catch (ItemNotFoundException e) {
			halt(404, e.getLocalizedMessage());
		}
	}
}
