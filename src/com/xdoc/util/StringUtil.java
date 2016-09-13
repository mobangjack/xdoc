/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮杰 (mobangjack@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xdoc.util;

import java.util.regex.Pattern;

/**
 * StringUtil.
 * @author 帮杰
 *
 */
public class StringUtil {

	/**
	 * Return true if the given String is null or has it's length==0.
	 * @param s
	 * @return True if the given String is null or has it's length==0.
	 */
	public static boolean isEmpty(String s) {
		return s==null||s.isEmpty();
	}
	
	/**
	 * Return true if the given String contains no visible characters.
	 * @param s
	 * @return True if the given String contains no visible characters.
	 */
	public static boolean isBlank(String s) {
		if (isEmpty(s)) {
			return true;
		}
		return !Pattern.compile("\\S").matcher(s).find();
	}
	
	/**
	 * Remove all invisible characters (match the regular expression "\s") in the given String.
	 * @param s
	 * @return The new String that contains no invisible characters,or null if the given String is null.
	 */
	public static String removeInvisibleChars(String s) {
		if (s==null) {
			return null;
		}
		if (Pattern.compile("\\s").matcher(s).find()) {
			return s.replaceAll("\\s", "");
		}
		return s;
	}
	
	/**
	 * Remove line separator in the given String.
	 * @param s
	 * @return
	 */
	public static String removeSystemLineSeparator(String s) {
		if (s==null) {
			return null;
		}
		String lineSeparator = System.getProperty("line.separator");
		if (s.contains(lineSeparator)) {
			return s.replace(lineSeparator, "");
		}
		return s;
	}
}
