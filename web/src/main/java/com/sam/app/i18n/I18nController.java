package com.sam.app.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class I18nController {
	
	private static final String BUNDLE_NAME = "com.sam.app.i18n.Messages";
	
	private static ConcurrentHashMap<Locale, ResourceBundle> bundles = new ConcurrentHashMap<Locale, ResourceBundle>(); 
	
	public static String getString(String name, Locale locale) {		
		ResourceBundle messages = getBundle(locale);
		return messages.getString(name);
	}
	
	private static ResourceBundle getBundle(Locale locale) {
		ResourceBundle bundle = bundles.get(locale);
		if (bundle == null) {
			bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
			bundles.putIfAbsent(locale, bundle);
		}
		return bundles.get(locale);
	}

}
