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
package com.xdoc.test;

import java.io.IOException;

import com.xdoc.Document;
import com.xdoc.XDoc;
import com.xdoc.element.Element;
import com.xdoc.element.Node;
import com.xdoc.element.Text;
import com.xdoc.element.UnaryTag;
import com.xdoc.selector.logic.LogicSelector;
import com.xdoc.writer.DocumentWriter;

/**
 * @author 帮杰
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {
		//File file = new File("G:/html/19-blog（个人博客模板-执子之手与子偕老）/19-blog（个人博客模板-执子之手与子偕老）/index.html");
		String url = "http://vhost.whu.edu.cn/gh/xywh.php?Class_Type=0&Class_ID=42";
		String charset = "gbk";
		String src = XDoc.read(url, charset);
		Document doc = XDoc.parse(src);
		DocumentWriter writer = new DocumentWriter();
		System.out.println(writer.write(doc));
		doc.setOnDocumentChangeListener(new MyDocumentChangeListener());
		Node node = new Node("testNode");
		node.addChildElements("This is a text.");
		doc.addChildElement(node);
		System.out.println(writer.write(doc));
	}
	
	public static void testSelector() throws IOException {
		String url = "http://vhost.whu.edu.cn/gh/xywh.php?Class_Type=0&Class_ID=42";
		String charset = "gbk";
		String src = XDoc.read(url, charset);
		Document doc = XDoc.parse(src);
		LogicSelector logicSelector = new LogicSelector() {

			@Override
			public boolean selectNode(Node node) {
				return false;
			}

			@Override
			public boolean selectUnaryTag(UnaryTag unaryTag) {
				return false;
			}

			@Override
			public boolean selectText(Text text) {
				return true;
			}
		};
		logicSelector.setDepth(4);
		for (Element e : doc.getElements(logicSelector)) {
			System.out.println(e);
		}
	}
}
