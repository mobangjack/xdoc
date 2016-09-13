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
package com.xdoc.writer;

import java.util.HashMap;
import java.util.Map;

import com.xdoc.element.Attribute;
import com.xdoc.element.Element;
import com.xdoc.element.Node;
import com.xdoc.element.Text;
import com.xdoc.element.UnaryTag;
import com.xdoc.writer.listener.OnWriteListener;
import com.xdoc.writer.listener.OnWriteListenerAdaptor;

/**
 * NodeWriter for writing node.Custom format support.
 * @author 帮杰
 *
 */
public class NodeWriter {

	protected StringBuilder sb = new StringBuilder();
	private boolean autoEscape = false;
	private boolean autoIndent = true;
	private int indentSize = 1;
	private int referenceNodeDepth = 0;
	private String indentUnit = "\t";
	private Map<Element, Integer> depthMap = new HashMap<Element, Integer>();
	private OnWriteListener onWriteListener = new OnWriteListenerAdaptor();
	
	public NodeWriter() {
	}
	
	public StringBuilder write(Node node) {
		depthMap.put(node, referenceNodeDepth);
		writeNode(sb, node);
		return sb;
	}
	
	public void writeNode(StringBuilder sb,Node node) {
		if (depthMap.get(node)>=0) {
			handleAutoIndent(node);
		}
		sb.append("<").append(node.getName());
		for (Attribute attribute : node.getAttributes()) {
			writeAttribute(attribute);
		}
		sb.append(">");
		for (Element element : node.getChildElements()) {
			depthMap.put(element, depthMap.get(node)+1);
			if (element instanceof Node) {
				writeNode(sb, (Node)element);
			}else if (element instanceof UnaryTag) {
				writeUnaryTag((UnaryTag)element);
			}else if (element instanceof Text) {
				writeText((Text)element);
			}
		}
		handleAutoIndent(node);
		sb.append("</").append(node.getName()).append(">");
	}
	
	protected void writeSystemLineSeparator() {
		sb.append(System.getProperty("line.separator"));
	}
	
	protected void handleAutoIndent(Element element) {
		if (isAutoIndent()) {
			writeSystemLineSeparator();
			for (int i = 0; i < depthMap.get(element); i++) {
				for (int j = 0; j < getIndentSize(); j++) {
					sb.append(indentUnit);
				}
			}
		}
	}
	
	protected void writeUnaryTag(UnaryTag unaryTag) {
		onWriteListener.onWriteUnitaryTag(sb, unaryTag);
		handleAutoIndent(unaryTag);
		sb.append(unaryTag);
	}

	protected void writeText(Text text) {
		if (!text.isBlank()) {
			onWriteListener.onWriteText(sb, text);
			handleAutoIndent(text);
			if (isAutoEscape()) {
				escape(sb, text.getContent());
			}else {
				sb.append(text.getContent());
			}
		}
	}
	
	protected void writeAttribute(Attribute attribute) {
		sb.append(" ").append(attribute);
	}
	
	public static void escape(StringBuilder sb,String s) {
		for (int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			switch (c) {
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '\"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
	}

	public boolean isAutoEscape() {
		return autoEscape;
	}

	public void setAutoEscape(boolean autoEscape) {
		this.autoEscape = autoEscape;
	}

	public boolean isAutoIndent() {
		return autoIndent;
	}

	public void setAutoIndent(boolean autoIndent) {
		this.autoIndent = autoIndent;
	}

	public String getIndentUnit() {
		return indentUnit;
	}

	public void setIndentUnit(String indentUnit) {
		this.indentUnit = indentUnit;
	}

	public int getIndentSize() {
		return indentSize;
	}

	public void setIndentSize(int indentSize) {
		this.indentSize = indentSize;
	}

	public Map<Element, Integer> getDepthMap() {
		return depthMap;
	}

	public OnWriteListener getOnWriteListener() {
		return onWriteListener;
	}

	public void setOnWriteListener(OnWriteListener onWriteListener) {
		if (onWriteListener!=null) {
			this.onWriteListener = onWriteListener;
		}
	}

	public int getReferenceNodeDepth() {
		return referenceNodeDepth;
	}

	public void setReferenceNodeDepth(int referenceNodeDepth) {
		this.referenceNodeDepth = referenceNodeDepth;
	}

}
