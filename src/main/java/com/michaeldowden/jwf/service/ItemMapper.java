package com.michaeldowden.jwf.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.michaeldowden.jwf.model.Bourbon;

public interface ItemMapper {

	@Results(
		id = "bourbonResult",
		value = {
			@Result(property = "id", column = "id", id = true),
			@Result(property = "name", column = "name"),
			@Result(property = "description", column = "description"),
			@Result(property = "price", column = "price", javaType = BigDecimal.class),
			@Result(property = "shortname", column = "shortname")
		}
	)
	@Select("SELECT * FROM store.items")
	public List<Bourbon> listItems();

	@ResultMap("bourbonResult")
	@Select("SELECT * FROM store.items WHERE id = #{itemId}")
	public Bourbon findBourbon(Integer itemId);

	@ResultMap("bourbonResult")
	@Select("SELECT * FROM store.items WHERE shortname = #{shortname}")
	public Bourbon findBourbonByShortname(String shortname);

}
