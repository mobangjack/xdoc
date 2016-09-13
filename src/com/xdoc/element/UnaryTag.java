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


/**
 * 一元标签
 * @author 帮杰
 *
 */
public class UnaryTag extends Tag {

	public UnaryTag(String name) {
		super(name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UnaryTag) {
			UnaryTag tag = (UnaryTag)obj;
			return tag.hasName(getName())&&tag.hasAttributes(getAttributes());
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(getName());
		for (Attribute attribute : getAttributes()) {
			sb.append(" ").append(attribute);
		}
		sb.append("/>");
		return sb.toString();
	}
}
