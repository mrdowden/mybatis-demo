package com.michaeldowden.jwf.utils;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;

import com.michaeldowden.jwf.model.OrderItem;
import com.michaeldowden.jwf.model.ShoppingCart;

public final class CartUtils {
	private CartUtils() {
	}

	public static void calculateTotal(ShoppingCart shoppingCart) {
		BigDecimal total = BigDecimal.valueOf(0.0);
		for (OrderItem item : shoppingCart.getItems()) {
			BigDecimal subtotal = item.getPrice().multiply(valueOf(item.getQty()));
			total = total.add(subtotal);
		}
		shoppingCart.setTotal(total);
	}

	private static int findItemInCart(ShoppingCart shoppingCart, Integer itemId) {
		for (int i = 0; i < shoppingCart.getItems().size(); i++) {
			OrderItem item = shoppingCart.getItems().get(i);
			if (item.getId() == itemId) {
				return i;
			}
		}
		return -1;
	}

	public static void addToCart(ShoppingCart shoppingCart, OrderItem newItem) {
		int i = findItemInCart(shoppingCart, newItem.getId());
		if (i < 0) {
			// Add a new item to the cart
			shoppingCart.getItems().add(newItem);
		} else {
			// Update quantity for existing item
			OrderItem cartItem = shoppingCart.getItems().get(i);
			cartItem.setQty(cartItem.getQty() + newItem.getQty());
		}
		calculateTotal(shoppingCart);
	}

	public static void updateQuantity(ShoppingCart shoppingCart, Integer itemId, Integer qty)
			throws ItemNotFoundException {
		int i = findItemInCart(shoppingCart, itemId);
		if (i < 0) {
			throw new ItemNotFoundException("Item doesn't exist in Shopping Cart");
		} else {
			OrderItem cartItem = shoppingCart.getItems().get(i);
			cartItem.setQty(qty);
		}
		calculateTotal(shoppingCart);
	}

	public static void removeFromCart(ShoppingCart shoppingCart, Integer itemId)
			throws ItemNotFoundException {
		int i = findItemInCart(shoppingCart, itemId);
		if (i < 0) {
			throw new ItemNotFoundException("Item doesn't exist in Shopping Cart");
		} else {
			shoppingCart.getItems().remove(i);
		}
		calculateTotal(shoppingCart);
	}

}
