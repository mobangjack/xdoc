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
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @author 帮杰
 *
 */
public class DocumentEventTriggerArrayList<E extends DocumentEventTrigger> extends ArrayList<E> {

	private static final long serialVersionUID = 8809000243891883507L;

	public DocumentEventTriggerArrayList() {
		super();
	}

	public DocumentEventTriggerArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	
	public DocumentEventTriggerArrayList(Collection<? extends E> c) {
		super(c);
	}

	@Override
	public boolean add(E e) {
		e.beforeAddElement();
		super.add(e);
		e.afterAddElement();
		return true;
	}
	
	@Override
	public void add(int index, E element) {
		element.beforeAddElement();
		super.add(index, element);
		element.afterAddElement();
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return true;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E e : c) {
			add(index, e);
			index++;
		}
		return false;
	}
	
	@Override
	public E remove(int index) {
		E e = get(index);
		e.beforeRemoveElement();
		e = super.remove(index);
		e.afterRemoveElement();;
		return e;
	}
	
	protected void resetParentNode(Node node) {
		for (Element e : node.getChildElements()) {
			e.setParentNode(node.getParentNode());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (contains(o)) {
			((E)o).beforeRemoveElement();
			super.remove(o);
			if (o instanceof Node) {
				Node node = (Node)o;
				resetParentNode(node);
			}
			((E)o).afterRemoveElement();
		}
		return false;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean flag = false;
		for (Object object : c) {
			if (remove(object)&&flag==false) {
				flag = true;
			}
		}
		return flag;
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		boolean flag = false;
		for (E e : this) {
			if (filter.test(e)) {
				if (remove(e)&&flag==false) {
					flag = true;
				}
			}
		}
		return flag;
	}
	
	@Override
	public void removeRange(int fromIndex, int toIndex) {
		while (fromIndex<toIndex) {
			remove(fromIndex);
			fromIndex++;
		}
	}
	
	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		for (E e : this) {
			e.beforeElementChange();
			operator.apply(e).afterElementChange();
		}
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean flag = false;
		for (E e : this) {
			if (!c.contains(e)) {
				e.beforeRemoveElement();
				remove(e);
				e.afterRemoveElement();
				flag = flag==false?true:flag;
			}
		}
		return false;
	}
	
	@Override
	public E set(int index, E element) {
		get(index).beforeElementChange();
		E e = super.set(index, element);
		element.afterElementChange();
		return e;
	}
}
