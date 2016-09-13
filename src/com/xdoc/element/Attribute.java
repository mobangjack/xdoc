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
 * Attribute consists of name(not null or empty) and value..
 * @author 帮杰
 *
 */
public class Attribute extends DocumentEventTrigger {

	private String name = "";
	private String value = "";
	
	public Attribute(String name,String value) {
		this.name = name;
		this.value = value;
	}

	public void set(String name,String value) {
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Attribute name can not be blank.");
		}
		if (!equals(name, value)) {
			beforeElementChange();
			this.name = name;
			this.value = value;
			afterElementChange();
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Attribute name can not be blank.");
		}
		if (!hasName(name)) {
			beforeElementChange();
			this.name = StringUtil.removeInvisibleChars(name);
			afterElementChange();
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (!hasValue(value)) {
			beforeElementChange();
			this.value = value;
			afterRemoveElement();
		}
	}
	
	public boolean hasName(String name) {
		name = name==null?"":name;
		return getName().equals(name);
	}
	
	public boolean hasValue(String value) {
		value = value==null?"":value;
		return getValue().equals(value);
	}
	
	public boolean equals(String name,String value) {
		return hasName(name)&&hasValue(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Attribute){
			Attribute attribute = (Attribute)obj;
			return attribute.hasName(name)&&attribute.hasValue(value);
		}
		return super.equals(obj);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Attribute(getName(), getValue());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append("=").append(value.contains("\"")?"'"+value+"'":"\""+value+"\"");
		return sb.toString();
	}

	@Override
	protected Element getTriggerElement() {
		return getParentNode();
	}

	@Override
	protected void setParentNode(Node parentNode) {
		super.setParentNode(parentNode);
	}
}
