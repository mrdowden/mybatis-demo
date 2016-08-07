package com.michaeldowden.store.model;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

import java.math.BigDecimal;
import java.util.List;

public class Order {
	private Integer orderNumber;
	private List<OrderItem> items = emptyList();
	private Address address = new Address();
	private BigDecimal total = BigDecimal.valueOf(0.0);

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = unmodifiableList(items);
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
