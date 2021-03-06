package com.michaeldowden.store.web;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.put;

import java.time.LocalDateTime;

import org.apache.shiro.SecurityUtils;

import com.google.gson.Gson;
import com.michaeldowden.store.model.Bourbon;
import com.michaeldowden.store.service.ItemMapper;

public class AdminController {

	private final Gson gson = new Gson();
	private final ItemMapper mapper;

	public AdminController(ItemMapper mapper) {
		this.mapper = mapper;
	}

	public void initialize() {
		get("/svc/admin/items", "application/json", (req, res) -> {
			return mapper.listItems();
		}, gson::toJson);

		get("/svc/admin/item/:shortname", "application/json", (req, res) -> {
			Bourbon item = mapper.findBourbonByShortname(req.params(":shortname"));
			if (item == null) {
				halt(404);
			}
			return item;
		}, gson::toJson);

		put("/svc/admin/items", "application/json", (req, res) -> {
			Bourbon bourbon = gson.fromJson(req.body(), Bourbon.class);
			
			bourbon.setLastUpdated( LocalDateTime.now() );
			bourbon.setWhoUpdated( (String)SecurityUtils.getSubject().getPrincipal() );
			
			if (bourbon.getId() == null) {
				mapper.insertBourbon(bourbon);
			} else {
				mapper.updateBourbon(bourbon);
			}

			return Boolean.TRUE;
		});
	}

}
