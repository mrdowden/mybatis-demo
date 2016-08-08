package com.michaeldowden.store.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.michaeldowden.pluralsight.mybatis.LastUpdatedMetadata;

public class Bourbon implements LastUpdatedMetadata, Serializable {
	private static final long serialVersionUID = -7604498355924738924L;

	private Integer id;
	private String name;
	private String description;
	private BigDecimal price;
	private String shortname;
	private LocalDateTime lastUpdated;
	private String whoUpdated;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getWhoUpdated() {
		return whoUpdated;
	}

	public void setWhoUpdated(String whoUpdated) {
		this.whoUpdated = whoUpdated;
	}

}
