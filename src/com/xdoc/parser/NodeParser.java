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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdoc.element.Node;
import com.xdoc.element.Tag;
import com.xdoc.element.Text;
import com.xdoc.element.UnaryTag;
import com.xdoc.util.StringUtil;

/**
 * NodeParser for parsing Node Object from String.
 * @author 帮杰
 *
 */
public class NodeParser {

	private boolean autoUnescape = false;
	private boolean autoUncdata = false;
	
	public NodeParser() {}
	
	public boolean isAutoUnescape() {
		return autoUnescape;
	}

	public boolean isAutoUncdata() {
		return autoUncdata;
	}

	public void setAutoUncdata(boolean autoUncdata) {
		this.autoUncdata = autoUncdata;
	}

	public void setAutoUnescape(boolean autoUnescape) {
		this.autoUnescape = autoUnescape;
	}

	public Node parseNode(String s) {
		if (StringUtil.isBlank(s)) {
			return null;
		}
		s = StringUtil.removeSystemLineSeparator(s);
		Pattern p = Pattern.compile("<\\s*(\\w+)\\s*([^<>]*)\\s*>([\\s\\S]*)<\\s*/\\s*\\1\\s*>");
		Matcher m = p.matcher(s);
		if (!m.find()) {
			return null;
		}
		String name = m.group(1);
		String attribute = m.group(2);
		String content = m.group(3);
		Node node = new Node(name);
		parseAttribute(node, attribute);
		parseNodeContent(node, content);
		return node;
	}
	
	protected void parseAttribute(Tag tag,String attribute) {
		if (StringUtil.isBlank(attribute)) {
			return;
		}
		Pattern p = Pattern.compile("(\\w+)=((\"[^\"]*?\")|('[^']*?'))");
		Matcher m = p.matcher(attribute);
		while (m.find()) {
			//attribute name
			String k = m.group(1);
			//attribute value
			String v = m.group(2);
			//unquot
			v = unquot(v);
			//add attribute to the tag
			tag.setAttribute(k, v);
		}
	}
	
	protected void parseNodeContent(Node node,String content) {
		if (StringUtil.isBlank(content)) {
			return;
		}
		Pattern p = Pattern.compile("<\\s*(\\w+)\\s*([^<>]*)/?\\s*>");
		Matcher m = p.matcher(content);
		if (m.find()) {
			String beginTag = m.group();
			String tagName = m.group(1);
			String attribute = m.group(2);
			
			//index of beginTag
			int beginIndex = content.indexOf(beginTag);
			//text
			if (beginIndex>0) {
				String text = content.substring(0, beginIndex);
				parseText(node, text);
			}
			//find endTag it's index
			String endTag = null;
			int endIndex = 0;
			
			int openTagCount = 1;
			int closeTagCount = 0;
			int fromIndex = beginIndex+beginTag.length();
			String fromContent = content.substring(fromIndex);
			Pattern pp = Pattern.compile("(<\\s*"+tagName+"\\s*([^<>]*)\\s*>)|(<\\s*/\\s*"+tagName+"\\s*>)");
			Matcher mm = pp.matcher(fromContent);
			while ((openTagCount!=closeTagCount)&&mm.find()) {
				String tag = mm.group();
				if (isOpenTag(tag)) {
					openTagCount++;
				}else {
					closeTagCount++;
					endTag = tag;
					endIndex = content.indexOf(endTag, fromIndex) + endTag.length();
				}
				fromIndex = content.indexOf(tag, fromIndex)+tag.length();
				fromContent = content.substring(fromIndex);
				mm = pp.matcher(fromContent);
			}
			if (openTagCount==closeTagCount) {
				//find endTag
				String childNodeString = content.substring(beginIndex, endIndex);
				Node childNode = parseNode(childNodeString);
				node.addChildElement(childNode);
			}else {
				endTag = beginTag;
				endIndex = beginIndex + beginTag.length();
				UnaryTag unaryTag = new UnaryTag(tagName);
				parseAttribute(unaryTag, attribute);
				node.addChildElement(unaryTag);
			}
			//last content
			if (endIndex<content.length()) {
				String nextContent = content.substring(endIndex);
				parseNodeContent(node, nextContent);
			}
		}else {
			parseText(node, content);
		}
	}
	
	protected boolean isOpenTag(String tag) {
		return Pattern.compile("<\\s*\\w+\\s*([^<>]*)\\s*>").matcher(tag).matches();
	}
	
	protected boolean isCloseTag(String tag) {
		return Pattern.compile("<\\s*/\\w+\\s*>").matcher(tag).matches();
	}
	
	protected void parseText(Node node,String text) {
		if (StringUtil.isBlank(text)) {
			return;
		}else if (isAutoUncdata()) {
			StringBuilder sb = new StringBuilder();
			Pattern p = Pattern.compile("<!\\[CDATA\\[(.*?)\\]\\]>");
			Matcher m = p.matcher(text);
			int i = 0;
			String cdata;
			String data;
			String nocdata;
			while (m.find()) {
				cdata = m.group();
				data = m.group(1);
				nocdata = text.substring(i, i=text.indexOf(cdata, i));
				sb.append(isAutoUnescape()?unescape(nocdata):nocdata);
				sb.append(data);
				i += cdata.length();
			}
			if (i<text.length()-1) {
				nocdata = text.substring(i, text.length());
				sb.append(isAutoUnescape()?unescape(nocdata):nocdata);
			}
			node.addChildElement(new Text(sb.toString()));
		}else {
			node.addChildElement(new Text(isAutoUnescape()?unescape(text):text));
		}
		
	}
	
	protected String unquot(String s) {
		if ((s.startsWith("'")&&s.endsWith("'"))||(s.startsWith("\"")&&s.endsWith("\""))) {
			return s.substring(1, s.length()-1);
		}else {
			return s;
		}
	}
	
	/**
	 * unescape &amp;&lt;&gt;&quot;&apos;
	 * @param s
	 * @return
	 */
	public static String unescape(String s) {
		if (s.contains("&amp;")) {
			s = s.replaceAll("&amp;", "&");
		} else if (s.contains("&lt;")) {
			s = s.replaceAll("&lt;", "<");
		} else if (s.contains("&gt;")) {
			s = s.replaceAll("&gt;", ">");
		} else if (s.contains("&quot;")) {
			s = s.replaceAll("&quot;", "\"");
		} else if (s.contains("&apos;")) {
			s = s.replaceAll("&apos;", "'");
		}
		return s;
	}
	
	public static NodeParser getDefaultNodeParser() {
		return DEFAULT_NODE_PARSER;
	}

	private static final NodeParser DEFAULT_NODE_PARSER = new NodeParser();
	
}
