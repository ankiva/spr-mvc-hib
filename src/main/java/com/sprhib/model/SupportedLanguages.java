package com.sprhib.model;

import java.util.Locale;

public enum SupportedLanguages {
	EN("English"), ET("eesti"), ;

	private String languageName;

	private SupportedLanguages(String languageName) {
		this.languageName = languageName;
	}

	public String getCode() {
		return name().toLowerCase();
	}

	public String getLanguageName() {
		return this.languageName;
	}

	public static SupportedLanguages parse(String value) {
		for (SupportedLanguages lang : values()) {
			if (lang.getCode().equals(lang)) {
				return lang;
			}
		}
		return null;
	}

	public static SupportedLanguages valueOf(Locale locale) {
		if (locale != null) {
			for (SupportedLanguages lang : values()) {
				if (lang.getCode().equals(locale.getLanguage())) {
					return lang;
				}
			}
		}
		return null;
	}
}
