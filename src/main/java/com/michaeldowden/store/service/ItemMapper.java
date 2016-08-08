package com.michaeldowden.store.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.michaeldowden.store.model.Bourbon;
import com.michaeldowden.store.utils.LocalDateTimeTypeHandler;

public interface ItemMapper {

	@Results(
		id = "bourbonResult",
		value = {
			@Result(property = "id", column = "id", id = true),
			@Result(property = "name", column = "name"),
			@Result(property = "description", column = "description"),
			@Result(property = "price", column = "price", javaType = BigDecimal.class),
			@Result(property = "shortname", column = "shortname"),
			@Result(property = "lastUpdated", column = "lastUpdated", 
				javaType = LocalDateTime.class, 
				typeHandler = LocalDateTimeTypeHandler.class),
			@Result(property = "whoUpdated", column = "whoUpdated")
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

	@Insert("INSERT INTO items (name, description, price, shortname, lastUpdated, whoUpdated) "
			+ "VALUES ( #{name}, #{description}, #{price}, #{shortname}, "
			+ "#{lastUpdated,typeHandler=com.michaeldowden.store.utils.LocalDateTimeTypeHandler}, "
			+ "#{whoUpdated} )")
	public void insertBourbon(Bourbon bourbon);
	
	@Update("UPDATE items SET name = #{name}, description = #{description}, price = #{price}, "
			+ "shortname = #{shortname}, lastUpdated = #{lastUpdated,typeHandler=com.michaeldowden.store.utils.LocalDateTimeTypeHandler}, whoUpdated = #{whoUpdated}"
			+ "WHERE id = #{id}")
	public void updateBourbon(Bourbon bourbon);
	
}
