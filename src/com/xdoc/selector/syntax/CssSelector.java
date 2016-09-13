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
package com.xdoc.selector.syntax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xdoc.element.Elements;
import com.xdoc.selector.logic.LogicSelector;



/**
 * CSS选择器
 * @author 帮杰
 *
 */
public class CssSelector extends SyntaxSelector {

	private static final Map<String, LogicSelector> UNARY_SELECTION_MAP = new HashMap<String, LogicSelector>();
	
	public static LogicSelector putUnarySelectionMapping(String unarySelectionString,LogicSelector unaryLogicSelector) {
		return UNARY_SELECTION_MAP.put(unarySelectionString, unaryLogicSelector);
	}
	
	static {
		//UNARY_SELECTION_MAP.put("", n);
	}
	
	public CssSelector(String css) {
		super(css);
	}

	@Override
	public List<String> breakSententceToUnarySymbols() {
		return null;
	}

	@Override
	public Map<String, LogicSelector> getUnaryOperationMap() {
		return null;
	}

	public static Elements select(String css,Elements elements) {
		return new CssSelector(css).select(elements);
	}

	public static Map<String, LogicSelector> getUnarySelectionMap() {
		return UNARY_SELECTION_MAP;
	}
	
}
