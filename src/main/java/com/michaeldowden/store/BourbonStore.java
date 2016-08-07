package com.michaeldowden.store;

import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import com.michaeldowden.store.web.AdminController;
import com.michaeldowden.store.web.CartController;
import com.michaeldowden.store.web.ItemController;
import com.michaeldowden.store.web.OrderController;

public class BourbonStore {

	public static void main(String[] args) throws Exception {
		staticFileLocation("/template/");
		port(8080); // Match traditional JEE port

		new ItemController().initialize();
		new CartController().initialize();
		new OrderController().initialize();
		new AdminController().initialize();
	}

}
