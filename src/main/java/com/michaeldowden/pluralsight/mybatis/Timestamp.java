package com.michaeldowden.pluralsight.mybatis;

import java.time.LocalDateTime;

public interface Timestamp {

	LocalDateTime getUpdatedAt();

	void setUpdatedAt(LocalDateTime date);

	LocalDateTime getCreatedAt();

	void setCreatedAt(LocalDateTime date);

}
