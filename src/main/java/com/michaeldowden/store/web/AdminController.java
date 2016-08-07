package com.michaeldowden.store.web;

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;
import com.michaeldowden.store.model.Bourbon;
import com.michaeldowden.store.service.ItemDao;

public class AdminController {

	private final Gson gson = new Gson();
	private final ItemDao itemDao = ItemDao.getInstance();

	public void initialize() {
		get("/svc/admin/items", "application/json", (req, res) -> {
			return itemDao.listItems();
		}, gson::toJson);

		post("/svc/admin/item", "application/json", (req, res) -> {
			itemDao.storeBourbon(gson.fromJson(req.body(), Bourbon.class));
			return Boolean.TRUE;
		});
	}

}
