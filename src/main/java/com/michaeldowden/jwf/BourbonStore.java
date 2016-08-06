package com.michaeldowden.jwf;

import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import com.michaeldowden.jwf.web.CartController;
import com.michaeldowden.jwf.web.ItemController;
import com.michaeldowden.jwf.web.OrderController;

public class BourbonStore {

	public static void main(String[] args) throws Exception {
		staticFileLocation("/template/");
		port(8080); // Match traditional JEE port

		new ItemController().initialize();
		new CartController().initialize();
		new OrderController().initialize();
	}

}
