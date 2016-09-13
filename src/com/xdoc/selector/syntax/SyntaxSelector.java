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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xdoc.element.Elements;
import com.xdoc.selector.Selector;
import com.xdoc.selector.logic.LogicSelector;


/**
 * 语法选择器
 * @author 帮杰
 *
 */
public abstract class SyntaxSelector implements SyntaxAnalyzer,UnaryOperationMapper,Selector {

	private String syntax;
	
	public SyntaxSelector(String syntax) {
		this.setSyntax(syntax);
	}
	
	public List<LogicSelector> generateSelectors() {
		List<String> unaryOperations = breakSententceToUnarySymbols();
		Map<String, LogicSelector> operationMap = getUnaryOperationMap();
		List<LogicSelector> logicSelectors = new ArrayList<LogicSelector>();
		for (String operation : unaryOperations) {
			if (operationMap.get(operation)!=null) {
				logicSelectors.add(operationMap.get(operation));
			}
		}
		return logicSelectors;
	}
	
	@Override
	public Elements select(Elements elements) {
		return select(this, this, elements);
	}

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}
	
	public static Elements select(SyntaxAnalyzer analyzer,UnaryOperationMapper unaryOperationMapper,Elements elements) {
		List<String> unaryOperations = analyzer.breakSententceToUnarySymbols();
		Map<String, LogicSelector> unaryOperationMap = unaryOperationMapper.getUnaryOperationMap();
		Elements selectElements = elements;
		for (String operation : unaryOperations) {
			if (unaryOperationMap.get(operation)!=null) {
				selectElements = unaryOperationMap.get(operation).select(selectElements);
			}
		}
		return selectElements;
	}
}
