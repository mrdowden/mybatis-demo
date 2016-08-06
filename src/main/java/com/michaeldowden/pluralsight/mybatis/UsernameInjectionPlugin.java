package com.michaeldowden.pluralsight.mybatis;

import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;

@Intercepts({
	@Signature(type = Executor.class, method = "query", args = { 
		MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class 
	})
})
public class UsernameInjectionPlugin implements Interceptor {

	public static final String USERNAME_PARAM_CONST = "USERNAME_PARAM";

	// Index of the parameterObject in the Executor's query method's args
	private static final int PARAMETER_INDEX = 1;

	private String parameterName;
	private String username;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		username = SecurityUtils.getSubject().getPrincipal().toString();

		final Object[] queryArgs = invocation.getArgs();

		if (queryArgs != null && queryArgs.length > PARAMETER_INDEX) {
			final Object parameterObject = queryArgs[PARAMETER_INDEX];
			if (parameterObject != null && parameterObject instanceof Map) {
				@SuppressWarnings("unchecked")
				final Map<String, Object> parameterMap = (Map<String, Object>) parameterObject;
				parameterMap.put(parameterName, username);
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
		parameterName = (String) properties.get(USERNAME_PARAM_CONST);
	}

}
