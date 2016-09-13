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

import com.xdoc.util.StringUtil;



/**
 * @author 帮杰
 *
 */
public class Tag extends Element {

	private String name = "";
	private Attributes attributes = new Attributes();
	
	public Tag(String name) {
		setName(name);
	}
	
	public boolean hasName(String name) {
		return getName().equals(name);
	}
	
	public void setAttribute(Attribute attribute) {
		if (!hasAttribute(attribute.getName())) {
			attribute.setParentNode(getParentNode());
			attributes.add(attribute);
		}else {
			getAttribute(attribute.getName()).setValue(attribute.getValue());
		}
	}

	public void setAttribute(String name,String value) {
		setAttribute(new Attribute(name,value));
	}
	
	public void setAttributes(Attributes attributes) {
		for (Attribute attribute : attributes) {
			setAttribute(attribute);
		}
	}
	
	public boolean hasAttribute(Attribute attribute) {
		return attributes.contains(attribute);
	}

	public boolean hasAttribute(String name) {
		for (Attribute attribute : attributes) {
			if (attribute.hasName(name)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasAttribute(String name,String value) {
		for (Attribute attribute : attributes) {
			if (attribute.equals(name, value)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasAttributes(Attributes attributes) {
		return getAttributes().containsAll(attributes);
	}
	
	public boolean hasAttributes(String... names) {
		for (String name : names) {
			if (!hasAttribute(name)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean removeAttribute(Attribute attribute) {
		if (getAttributes().contains(attribute)) {
			getAttributes().remove(attribute);
			return true;
		}
		return false;
	}
	
	public void removeAttribute(String name) {
		for (Attribute attribute : attributes) {
			if (attribute.hasName(name)) {
				getAttributes().remove(attribute);
			}
		}
	}
	
	public boolean removeAttribute(String name,String value) {
		for (Attribute attribute : attributes) {
			if (attribute.hasName(name)) {
				return removeAttribute(attribute);
			}
		}
		return false;
	}
	
	public void removeAttributes(String... names) {
		for (String name : names) {
			removeAttribute(name);
		}
	}
	
	public Attribute getAttribute(String name) {
		for (Attribute attribute : attributes) {
			if (attribute.hasName(name)) {
				return attribute;
			}
		}
		return null;
	}
	
	public String getAttributeValue(String name) {
		Attribute attribute = getAttribute(name);
		return attribute==null?"":attribute.getValue();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Tag name can not be blank!");
		}
		if (!hasName(name)) {
			beforeElementChange();
			this.name = name;
			afterElementChange();
		}
	}

	public Attributes getAttributes() {
		return attributes;
	}
	
	public boolean isSelfClosing() {
		return this instanceof UnaryTag;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			Tag tag = (Tag)obj;
			return tag.hasName(name)&&tag.hasAttributes(attributes);
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(name);
		for (Attribute attribute : attributes) {
			sb.append(" ").append(attribute);
		}
		sb.append("/>");
		return sb.toString();
	}

}
