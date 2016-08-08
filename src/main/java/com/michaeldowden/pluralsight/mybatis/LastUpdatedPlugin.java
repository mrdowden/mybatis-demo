package com.michaeldowden.pluralsight.mybatis;

import java.time.LocalDateTime;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.shiro.SecurityUtils;

/**
 * @see http://www.chenjianjx.com/myblog/entry/mybatis-automatically-set-timestamps-for
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class,
		Object.class }) })
public class LastUpdatedPlugin implements Interceptor {

	// Index of the Parameter in Executor.update
	private static final int PARAMETER_INDEX = 1;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final Object param = invocation.getArgs()[PARAMETER_INDEX];

		if (param != null && param instanceof LastUpdatedMetadata) {
			final LastUpdatedMetadata t = (LastUpdatedMetadata) param;

			if (t.getWhoUpdated() == null) {
				t.setWhoUpdated((String) SecurityUtils.getSubject().getPrincipal());
			}
			if (t.getLastUpdated() == null) {
				t.setLastUpdated(LocalDateTime.now());
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
