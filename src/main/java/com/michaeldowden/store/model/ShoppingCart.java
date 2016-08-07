package com.michaeldowden.store.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	private final List<OrderItem> items = new ArrayList<OrderItem>();
	private BigDecimal total = BigDecimal.valueOf(0.0);

	public List<OrderItem> getItems() {
		return items;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
