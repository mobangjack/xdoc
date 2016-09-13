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
public class Text extends Element {

	private String content = "";
	
	public Text() {
	}

	public Text(String content) {
		setContent(content);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		content = content==null?"":content;
		if (!content.equals(getContent())) {
			beforeElementChange();
			this.content = content;
			afterElementChange();
		}
	}

	public boolean isEmpty() {
		return getContent().equals("");
	}
	
	public boolean isBlank() {
		return StringUtil.isBlank(getContent());
	}
	
	@Override
	public String toString() {
		return getContent();
	}
}
