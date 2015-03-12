package net.kresina.bsc.javatest.components;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Conversions {
	private static final String BUNDLE_NAME = "net.kresina.bsc.javatest.components.conversions";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Conversions() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}
}
