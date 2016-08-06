package com.michaeldowden.pluralsight.mybatis;

import java.time.LocalDateTime;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * @see http://www.chenjianjx.com/myblog/entry/mybatis-automatically-set-timestamps-for
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class,
		Object.class }) })
public class TimestampUpdatePlugin implements Interceptor {

	// Index of MappedStatement parameter in Executor.update
	private static final int STATEMENT_INDEX = 0;

	// Index of the Parameter in Executor.update
	private static final int PARAMETER_INDEX = 1;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final MappedStatement stmt = (MappedStatement) invocation.getArgs()[STATEMENT_INDEX];
		final Object param = invocation.getArgs()[PARAMETER_INDEX];

		if (param != null && param instanceof Timestamp) {
			final Timestamp t = (Timestamp) param;

			if (SqlCommandType.INSERT.equals(stmt.getSqlCommandType())) {
				if (t.getCreatedAt() == null) {
					t.setCreatedAt(LocalDateTime.now());
				}
			} else if (SqlCommandType.UPDATE.equals(stmt.getSqlCommandType())) {
				if (t.getUpdatedAt() == null) {
					t.setUpdatedAt(LocalDateTime.now());
				}
			}

		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
