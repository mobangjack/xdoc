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

import com.xdoc.Document;
import com.xdoc.XDoc;




/**
 * Element
 * @author 帮杰
 *
 */
public class Element extends DocumentEventTrigger {

	public Element() {
	}

	@Override
	protected Element getTriggerElement() {
		return this;
	}
	
	@Override
	protected void setParentNode(Node parentNode) {
		super.setParentNode(parentNode);
	}
	
	public boolean hasSiblings() {
		if (getParentNode()!=null) {
			return !getParentNode().getChildElements().isEmpty();
		}
		return false;
	}
	
	public Elements siblings() {
		if (getParentNode()!=null) {
			return getParentNode().getChildElements();
		}
		return new Elements();
	}
	
	public Element firstSibling() {
		Elements elements = siblings();
		return elements.isEmpty()?null:elements.get(0);
	}
	
	public Element lastSibling() {
		Elements elements = siblings();
		return elements.isEmpty()?null:elements.get(elements.size()-1);
	}
	
	public Element before() {
		if (getParentNode()!=null) {
			int index = getParentNode().getChildElements().indexOf(this);
			if (index>0) {
				return getParentNode().getChildElements().get(index-1);
			}
		}
		return null;
	}
	
	public Element after() {
		if (getParentNode()!=null) {
			int index = getParentNode().getChildElements().indexOf(this);
			if (index>=0&&index<getParentNode().getChildElements().size()) {
				return getParentNode().getChildElements().get(index+1);
			}
		}
		return null;
	}

	public Elements prepend(String html) {
		Document doc = XDoc.parse(html);
		if (getParentNode()!=null) {
			doc.setParentNode(getParentNode());
			int index = getParentNode().getChildElements().indexOf(this);
			getParentNode().getChildElements().addAll(index, doc.getChildElements());
			return new Elements(getParentNode().getChildElements().subList(index, index+doc.getChildElements().size()));
		}else {
			Elements elements = new Elements();
			elements.addAll(doc.getChildElements());
			elements.add(this);
			return elements;
		}
	}

	public Elements append(String html) {
		Document doc = XDoc.parse(html);
		if (getParentNode()!=null) {
			doc.setParentNode(getParentNode());
			int index = getParentNode().getChildElements().indexOf(this);
			getParentNode().getChildElements().addAll(index+1, doc.getChildElements());
			return new Elements(getParentNode().getChildElements().subList(index, index+doc.getChildElements().size()));
		}else {
			Elements elements = new Elements();
			elements.add(this);
			elements.addAll(doc.getChildElements());
			return elements;
		}
	}
	
}
