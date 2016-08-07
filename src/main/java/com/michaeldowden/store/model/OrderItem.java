package com.michaeldowden.store.model;

import java.math.BigDecimal;

public class OrderItem {
	private Integer id;
	private Integer qty;
	private BigDecimal price;
	private String name;
	private String shortname;

	public OrderItem() {
	}

	public OrderItem(Bourbon item) {
		this.id = item.getId();
		this.price = item.getPrice();
		this.name = item.getName();
		this.shortname = item.getShortname();
		this.qty = 1;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

}
