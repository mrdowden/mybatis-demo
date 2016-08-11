package com.michaeldowden.store.web;

import static spark.Spark.get;
import static spark.Spark.halt;

import com.google.gson.Gson;
import com.michaeldowden.store.model.Bourbon;
import com.michaeldowden.store.service.ItemMapper;

public class ItemController {

	private final Gson gson = new Gson();
	private final ItemMapper mapper;
	
	public ItemController(ItemMapper mapper) {
		this.mapper = mapper;
	}

	public void initialize() {
		get("/svc/items", "application/json", (req, res) -> {
			return mapper.listItems();
		}, gson::toJson);

		get("/svc/item/:shortname", "application/json", (req, res) -> {
			Bourbon item = mapper.findBourbonByShortname(req.params(":shortname"));
			if (item == null) {
				halt(404);
			}
			return item;
		}, gson::toJson);
	}
}
