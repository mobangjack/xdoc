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

/**
 * @author 帮杰
 *
 */
public class Nodes extends DocumentEventTriggerArrayList<Node> {
	
	private static final long serialVersionUID = -1255591335171687468L;

	public Nodes() {
		super();
	}

	public Nodes(int initialCapacity) {
		super(initialCapacity);
	}
	
	public Nodes(Collection<? extends Node> c) {
		super(c);
	}
	
}
