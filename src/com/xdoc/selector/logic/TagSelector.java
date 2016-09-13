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
package com.xdoc.selector.logic;

import com.xdoc.element.Elements;
import com.xdoc.element.Node;
import com.xdoc.element.Tags;
import com.xdoc.element.Text;
import com.xdoc.element.UnaryTag;


/**
 * 标签选择器
 * @author 帮杰
 *
 */
public abstract class TagSelector extends LogicSelector {

	public TagSelector() {
	}
	
	public TagSelector(int depth) {
		super(depth);
	}
	
	@Override
	public boolean selectText(Text text) {
		return false;
	}
	
	public static Tags selectAllTags(Elements elements) {
		return new TagSelector() {
			@Override
			public boolean selectUnaryTag(UnaryTag unaryTag) {
				return true;
			}

			@Override
			public boolean selectNode(Node node) {
				return true;
			}
		}.selectTags(elements);
	}
	
}
