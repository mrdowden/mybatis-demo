package com.michaeldowden.store.utils;

public class ItemNotFoundException extends Exception {
	private static final long serialVersionUID = 6094154277888661019L;

	public ItemNotFoundException() {
		super();
	}

	public ItemNotFoundException(String message) {
		super(message);
	}
}
