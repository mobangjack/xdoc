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
package com.xdoc.element;

import java.util.Collection;

import com.xdoc.selector.logic.NodeSelector;
import com.xdoc.selector.logic.TagSelector;
import com.xdoc.selector.logic.TextSelector;
import com.xdoc.selector.logic.UnaryTagSelector;

/**
 * Elements
 * @author 帮杰
 *
 */
public class Elements extends DocumentEventTriggerArrayList<Element> {

	private static final long serialVersionUID = -6915533141366693199L;

	public Elements() {
		super();
	}

	public Elements(int initialCapacity) {
		super(initialCapacity);
	}
	
	public Elements(Collection<? extends Element> c) {
		super(c);
	}
	
	public Nodes getNodes() {
		return NodeSelector.selectAllNodes(this);
	}
	
	public Tags getTags() {
		return TagSelector.selectAllTags(this);
	}
	
	public UnaryTags getUnaryTags() {
		return UnaryTagSelector.selectAllUnaryTags(this);
	}
	
	public Texts getTexts() {
		return TextSelector.selectAllTexts(this);
	}
}
