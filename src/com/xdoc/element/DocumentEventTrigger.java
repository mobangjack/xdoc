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
 * @author 帮杰
 *
 */
public abstract class DocumentEventTrigger extends DocumentEventDriver {

	private Node parentNode;
	
	public DocumentEventTrigger() {
	}
	
	public boolean hasParentNode() {
		return getParentNode()!=null;
	}
	
	public Node getParentNode() {
		return parentNode;
	}

	protected void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	protected abstract Element getTriggerElement();
	
	public int getDepth() {
		int depth = 1;
		Node node = getParentNode();
		while (node!=null) {
			depth++;
			node = node.getParentNode();
		}
		return depth;
	}
	
	@Override
	protected void beforeAddElement() {
		Node node = getParentNode();
		Element element = getTriggerElement();
		while (node!=null&&element!=null) {
			node.getOnDocumentChangeListener().beforeAddElement(element);
			node = node.getParentNode();
		}
	}

	@Override
	protected void afterAddElement() {
		Node node = getParentNode();
		Element element = getTriggerElement();
		while (node!=null&&element!=null) {
			node.getOnDocumentChangeListener().afterAddElement(element);
			node = node.getParentNode();
		}
	}

	@Override
	protected void beforeRemoveElement() {
		Node node = getParentNode();
		Element element = getTriggerElement();
		while (node!=null&&element!=null) {
			node.getOnDocumentChangeListener().beforeRemoveElement(element);
			node = node.getParentNode();
		}
	}

	@Override
	protected void afterRemoveElement() {
		Node node = getParentNode();
		Element element = getTriggerElement();
		while (node!=null&&element!=null) {
			node.getOnDocumentChangeListener().afterRemoveElement(element);
			node = node.getParentNode();
		}
	}

	@Override
	protected void beforeElementChange() {
		Node node = getParentNode();
		Element element = getTriggerElement();
		while (node!=null&&element!=null) {
			node.getOnDocumentChangeListener().beforeElementChange(element);
			node = node.getParentNode();
		}
	}

	@Override
	protected void afterElementChange() {
		Node node = getParentNode();
		Element element = getTriggerElement();
		while (node!=null&&element!=null) {
			node.getOnDocumentChangeListener().afterElementChange(element);
			node = node.getParentNode();
		}
	}
}
