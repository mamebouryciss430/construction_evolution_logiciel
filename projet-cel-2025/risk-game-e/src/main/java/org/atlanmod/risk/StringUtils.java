package org.atlanmod.risk;

public final class StringUtils {

	private StringUtils() {} // classe non instanciable

	public static final String CLEAN_REGEX = "[0-9\\-]";

	public static String clean(String s) {
		return s == null ? "" : s.replaceAll(CLEAN_REGEX, "");
	}
}
