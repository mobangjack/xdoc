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

import com.xdoc.element.Element;
import com.xdoc.element.Elements;
import com.xdoc.element.Node;
import com.xdoc.element.Nodes;
import com.xdoc.element.Tags;
import com.xdoc.element.Text;
import com.xdoc.element.Texts;
import com.xdoc.element.UnaryTag;
import com.xdoc.element.UnaryTags;
import com.xdoc.selector.Selector;


/**
 * 选择器
 * @author 帮杰
 *
 */
public abstract class LogicSelector implements Selector {

	private int depth = 1;
	
	public LogicSelector() {
	}

	public LogicSelector(int depth) {
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}

	public abstract boolean selectNode(Node node);
	public abstract boolean selectUnaryTag(UnaryTag unaryTag);
	public abstract boolean selectText(Text text);
	
	@Override
	public Elements select(Elements elements) {
		Elements targets = new Elements();
		for (Element element : elements) {
			if (element instanceof Node) {
				Node node = (Node)element;
				if (selectNode(node)) {
					targets.add(node);
				}
				if (getDepth()>element.getDepth()) {
					targets.addAll(select(node.getChildElements()));
				}
			}else if (element instanceof UnaryTag) {
				if (selectUnaryTag((UnaryTag)element)) {
					targets.add(element);
				}
			}else if (element instanceof Text) {
				if (selectText((Text)element)) {
					targets.add(element);
				}
			}
		}
		return targets;
	}
	
	public Nodes selectNodes(Elements elements) {
		Nodes targets = new Nodes();
		for (Element element : elements) {
			if (element instanceof Node) {
				Node node = (Node)element;
				if (selectNode(node)) {
					targets.add(node);
				}
				if (getDepth()>element.getDepth()) {
					targets.addAll(selectNodes(node.getChildElements()));
				}
			}
		}
		return targets;
	}
	
	public Tags selectTags(Elements elements) {
		Tags targets = new Tags();
		for (Element element : elements) {
			if (element instanceof Node) {
				Node node = (Node)element;
				if (getDepth()>element.getDepth()) {
					targets.addAll(selectUnaryTags(node.getChildElements()));
				}
			}else if (element instanceof UnaryTag) {
				if (selectUnaryTag((UnaryTag)element)) {
					targets.add((UnaryTag)element);
				}
			}
		}
		return targets;
	}
	
	public UnaryTags selectUnaryTags(Elements elements) {
		UnaryTags targets = new UnaryTags();
		for (Element element : elements) {
			if (element instanceof Node) {
				Node node = (Node)element;
				if (getDepth()>element.getDepth()) {
					targets.addAll(selectUnaryTags(node.getChildElements()));
				}
			}else if (element instanceof UnaryTag) {
				if (selectUnaryTag((UnaryTag)element)) {
					targets.add((UnaryTag)element);
				}
			}
		}
		return targets;
	}
	
	public Texts selectTexts(Elements elements) {
		Texts targets = new Texts();
		for (Element element : elements) {
			if (element instanceof Node) {
				Node node = (Node)element;
				if (getDepth()>element.getDepth()) {
					targets.addAll(selectTexts(node.getChildElements()));
				}
			}else if (element instanceof Text) {
				if (selectText((Text)element)) {
					targets.add((Text)element);
				}
			}
		}
		return targets;
	}
	
}
