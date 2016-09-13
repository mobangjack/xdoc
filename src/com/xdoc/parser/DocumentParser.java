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
package com.xdoc.parser;

import com.xdoc.Document;
import com.xdoc.element.Node;

/**
 * DocumentParser for parsing Document and node.
 * @author 帮杰
 *
 */
public class DocumentParser extends NodeParser {

	public DocumentParser() {
		super();
	}

	public DocumentParser(String root) {
		super();
	}
	
	public Document parseDocument(String s) {
		if (s==null) {
			throw new IllegalArgumentException("The String Object to parse can not be null!");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(Document.ROOT).append(">").append(s).append("</").append(Document.ROOT).append(">");
		Node node = parseNode(sb.toString());
		Document document = new Document();
		document.setChildElements(node.getChildElements());
		return document;
	}

	public static DocumentParser getDefaultDocumentParser() {
		return DEFAULT_DOCUMENT_PARSER;
	}

	private static final DocumentParser DEFAULT_DOCUMENT_PARSER = new DocumentParser();
	
	
}
