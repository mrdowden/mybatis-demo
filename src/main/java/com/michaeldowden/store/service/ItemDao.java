package com.michaeldowden.store.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.michaeldowden.store.model.Bourbon;

public class ItemDao {
	private static final ItemDao SINGLETON = new ItemDao();
	private static final String CONFIG = "mybatis-config.xml";
	private static final String SCRIPT = "bourbon.sql";

	private SqlSessionFactory sqlSessionFactory;

	private ItemDao() {
		try {
			InputStream inputStream = Resources.getResourceAsStream(CONFIG);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			sqlSessionFactory = builder.build(inputStream);

			// Run Setup Script
			try (SqlSession session = sqlSessionFactory.openSession()) {
				ScriptRunner runner = new ScriptRunner(session.getConnection());
				runner.runScript(Resources.getResourceAsReader(SCRIPT));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static ItemDao getInstance() {
		return SINGLETON;
	}

	public List<Bourbon> listItems() {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			return mapper.listItems();
		}
	}

	public Bourbon findBourbon(final Integer itemId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			return mapper.findBourbon(itemId);
		}
	}

	public Bourbon findBourbonByShortname(final String shortname) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			return mapper.findBourbonByShortname(shortname);
		}
	}

	public void storeBourbon(final Bourbon bourbon) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			if (bourbon.getId() == null) {
				mapper.insertBourbon(bourbon);
			} else {
				mapper.updateBourbon(bourbon);
			}
		}
	}

}
