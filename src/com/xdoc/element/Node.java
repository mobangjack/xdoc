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
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.xdoc.listener.OnDocumentChangeListener;
import com.xdoc.listener.OnDocumentChangeListenerAdapter;
import com.xdoc.parser.NodeParser;
import com.xdoc.selector.Selector;
import com.xdoc.selector.logic.NodeSelector;
import com.xdoc.selector.logic.TagSelector;
import com.xdoc.selector.logic.TextSelector;
import com.xdoc.selector.logic.UnaryTagSelector;
import com.xdoc.selector.syntax.CssSelector;
import com.xdoc.selector.syntax.XpathSelector;

/**
 * Node Object
 * @author 帮杰
 *
 */
public class Node extends Tag {

	private Elements childElements;
	private OnDocumentChangeListener onDocumentChangeListener = new OnDocumentChangeListenerAdapter();
	
	public Node(String name) {
		super(name);
		childElements = new Elements();
	}
	
	public OnDocumentChangeListener getOnDocumentChangeListener() {
		return onDocumentChangeListener;
	}

	public void setOnDocumentChangeListener(OnDocumentChangeListener onDocumentChangeListener) {
		if (onDocumentChangeListener!=null) {
			this.onDocumentChangeListener = onDocumentChangeListener;
		}
	}

	public void setChildElements(Elements elements) {
		for (Element element : elements) {
			element.setParentNode(this);
			this.childElements.add(element);
		}
	}

	public Elements getElementsByCssSelector(String css) {
		return CssSelector.select(css, childElements);
	}
	
	public Elements getElementsByXpath(String xpath) {
		return XpathSelector.select(xpath, childElements);
	}
	
	public Elements getChildElements() {
		return childElements;
	}
	
	public Elements getElements(Selector selector) {
		return selector.select(childElements);
	}
	
	public Nodes getChildNodes() {
		return NodeSelector.selectAllNodes(childElements);
	}
	
	public Nodes getNodes(Selector selector) {
		return NodeSelector.selectAllNodes(selector.select(childElements));
	}
	
	public Tags getChildTags() {
		return TagSelector.selectAllTags(childElements);
	}
	
	public Tags getTags(Selector selector) {
		return TagSelector.selectAllTags(selector.select(childElements));
	}
	
	public UnaryTags getChildUnaryTags() {
		return UnaryTagSelector.selectAllUnaryTags(childElements);
	}
	
	public UnaryTags getUnaryTags(Selector selector) {
		return UnaryTagSelector.selectAllUnaryTags(selector.select(childElements));
	}
	
	public Texts getChildTexts() {
		return TextSelector.selectAllTexts(childElements);
	}
	
	public Texts getTexts(Selector selector) {
		return TextSelector.selectAllTexts(selector.select(childElements));
	}
	
	public Element getChildElement(int index) {
		return getChildElements().get(index);
	}
	
	public void addChildElement(Element e) {
		e.setParentNode(this);
		childElements.add(e);
	}
	
	public void addChildElement(int index,Element e) {
		e.setParentNode(this);
		getChildElements().add(index,e);
	}
	
	public void addChildElements(Collection<? extends Element> c) {
		for (Element element : c) {
			addChildElement(element);
		}
	}
	
	public void addChildElements(int index,Collection<? extends Element> c) {
		for (Element element : c) {
			addChildElement(index,element);
			index++;
		}
	}
	
	public void addChildElements(Object...objects) {
		for (Object object : objects) {
			if (object instanceof Element) {
				addChildElement((Element)object);
			}else if (object instanceof Elements) {
				addChildElements((Elements)object);
			}else if (object instanceof Nodes) {
				addChildElements((Nodes)object);
			}else if (object instanceof Tags) {
				addChildElements((Tags)object);
			}else if (object instanceof UnaryTags) {
				addChildElements((UnaryTags)object);
			}else if (object instanceof Texts) {
				addChildElements((Texts)object);
			}else if (object!=null) {
				addChildElement(new Text(object.toString()));
			}
		}
	}
	
	public Element removeChildElement(int index) {
		return getChildElements().remove(index);
	}
	
	public boolean removeChildElement(Element e) {
		e.setParentNode(this);
		return getChildElements().remove(e);
	}
	
	public void removeChildElements(Collection<? extends Element> c) {
		for (Element element : c) {
			removeChildElement(element);
		}
	}
	
	public boolean removeChildElements(Predicate<? super Element> filter) {
		return getChildElements().removeIf(filter);
	}
	
	public void removeChildElementsRange(int fromIndex,int toIndex) {
		getChildElements().removeRange(fromIndex, toIndex);
	}
	
	public boolean retainChildElements(Collection<? extends Element> c) {
		for (Element element : c) {
			element.setParentNode(this);
		}
		return getChildElements().retainAll(c);
	}
	
	public void replaceAllChildElement(UnaryOperator<Element> operator) {
		getChildElements().replaceAll(operator);
	}
	
	public Element setChildElement(int index,Element e) {
		e.setParentNode(this);
		return getChildElements().set(index, e);
	}
	
	public String getString() {
		StringBuilder sb = new StringBuilder();
		for (Element element : getChildElements()) {
			if (element instanceof Text) {
				sb.append(element);
			}else if (element instanceof Node) {
				sb.append(((Node)element).getString());
			}
		}
		return sb.toString();
	}
	
	public String getContent() {
		StringBuilder sb = new StringBuilder();
		for (Element element : getChildElements()) {
			sb.append(element);
		}
		return sb.toString();
	}
	
	public String getOpenTag() {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(getName());
		for (Attribute attribute : getAttributes()) {
			sb.append(" ").append(attribute);
		}
		sb.append(">");
		return sb.toString();
	}
	
	public String getCloseTag() {
		StringBuilder sb = new StringBuilder();
		sb.append("</").append(getName()).append(">");
		return sb.toString();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new NodeParser().parseNode(toString());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(getName());
		for (Attribute attribute : getAttributes()) {
			sb.append(" ").append(attribute);
		}
		sb.append(">");
		for (Object item : getChildElements()) {
			sb.append(item);
		}
		sb.append("</").append(getName()).append(">");
		return sb.toString();
	}
	
}
