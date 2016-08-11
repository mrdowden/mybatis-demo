package com.michaeldowden.store;

import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import com.michaeldowden.store.service.ItemMapper;
import com.michaeldowden.store.web.AdminController;
import com.michaeldowden.store.web.CartController;
import com.michaeldowden.store.web.ItemController;
import com.michaeldowden.store.web.OrderController;

public class BourbonStore implements Runnable {
	private static final String STATIC_PATH = "/template/";
	private static final String CONFIG = "mybatis-config.xml";
	private static final String SCRIPT = "bourbon.sql";

	/**
	 * Entry point into the application
	 * 
	 * @param args
	 *            ignored
	 */
	public static void main(String[] args) {
		new BourbonStore().run();
	}

	private final SqlSessionFactory sqlSessionFactory;

	/**
	 * Creates the BourbonStore and initializes the SqlSessionFactor for database access.
	 */
	private BourbonStore() {
		try {
			// Configure DataSource
			final InputStream inputStream = Resources.getResourceAsStream(CONFIG);
			final SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			sqlSessionFactory = builder.build(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Runs the application by performing configuration and binding to port 8080
	 */
	@Override
	public void run() {
		configureSecurity();

		staticFileLocation(STATIC_PATH);
		port(8080); // Match traditional JEE port

		final SqlSession session = sqlSessionFactory.openSession(true);
		populateSampleData(session);

		final ItemMapper mapper = session.getMapper(ItemMapper.class);

		// Initialize Controllers
		new ItemController(mapper).initialize();
		new CartController(mapper).initialize();
		new OrderController().initialize();
		new AdminController(mapper).initialize();
	}

	/**
	 * Configures Shiro from an INI file.
	 */
	private void configureSecurity() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(new UsernamePasswordToken("admin", "admin"));
	}

	/**
	 * Populates the database with Bourbon data
	 * 
	 * @param session
	 *            the database session
	 */
	private void populateSampleData(SqlSession session) {
		try {
			ScriptRunner runner = new ScriptRunner(session.getConnection());
			runner.runScript(Resources.getResourceAsReader(SCRIPT));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
