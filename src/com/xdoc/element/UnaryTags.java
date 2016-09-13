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

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 帮杰
 *
 */
public class UnaryTags extends ArrayList<UnaryTag> {

	private static final long serialVersionUID = -1248562863417238377L;

	public UnaryTags() {
		super();
	}

	public UnaryTags(int initialCapacity) {
		super(initialCapacity);
	}
	
	public UnaryTags(Collection<? extends UnaryTag> c) {
		super(c);
	}
	
}
