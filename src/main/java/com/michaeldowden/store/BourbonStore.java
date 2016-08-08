package com.michaeldowden.store;

import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import com.michaeldowden.store.web.AdminController;
import com.michaeldowden.store.web.CartController;
import com.michaeldowden.store.web.ItemController;
import com.michaeldowden.store.web.OrderController;

public class BourbonStore {

	public static void main(String[] args) throws Exception {
		configureSecurity();

		staticFileLocation("/template/");
		port(8080); // Match traditional JEE port

		new ItemController().initialize();
		new CartController().initialize();
		new OrderController().initialize();
		new AdminController().initialize();
	}

	/**
	 * Configures Shiro from an INI file.
	 */
	private static void configureSecurity() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(new UsernamePasswordToken("admin", "admin"));
	}

}
