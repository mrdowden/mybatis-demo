package com.michaeldowden.store.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter,
			JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, Timestamp.valueOf(parameter),
				GregorianCalendar.from(ZonedDateTime.of(parameter, ZoneId.systemDefault())));
	}

	@Override
	public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return fromTimestamp(rs.getTimestamp(columnName));
	}

	@Override
	public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return fromTimestamp(rs.getTimestamp(columnIndex));
	}

	@Override
	public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return fromTimestamp(cs.getTimestamp(columnIndex));
	}

	private LocalDateTime fromTimestamp(Timestamp ts) {
		return ts == null ? null : LocalDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
	}

}
