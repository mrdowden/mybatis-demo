package com.michaeldowden.pluralsight.mybatis;

import java.time.LocalDateTime;

public interface LastUpdatedMetadata {

	LocalDateTime getLastUpdated();

	void setLastUpdated(LocalDateTime date);

	String getWhoUpdated();

	void setWhoUpdated(String date);

}
