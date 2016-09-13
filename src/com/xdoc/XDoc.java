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
package com.xdoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.xdoc.parser.DocumentParser;

/**
 * @author 帮杰
 *
 */
public class XDoc {

	private XDoc() {
	}

	public static String read(Reader reader) throws IOException {
		StringBuilder sb = null;
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(reader);
			sb = new StringBuilder();
			String line;
			while ((line=bufferedReader.readLine())!=null) {
				sb.append(line).append(Document.LINE_SEPARATOR);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bufferedReader!=null) {
				bufferedReader.close();
				bufferedReader = null;
			}
		}
	}
	
	public static String read(InputStream input,String charset) throws IOException {
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(input,charset);
			return read(inputStreamReader);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (inputStreamReader!=null) {
				inputStreamReader.close();
				inputStreamReader = null;
			}
		}
	}
	
	public static String read(URL url,String charset,int timeout) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(timeout);
		InputStream input = connection.getInputStream();
		try {
			return read(input, charset);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (input!=null) {
				input.close();
				input = null;
			}
			if (connection!=null) {
				connection.disconnect();
				connection = null;
			}
		}
	}
	
	public static String read(String url,String charset,int timeout) throws IOException {
		return read(new URL(url), charset, timeout);
	}
	
	public static String read(String url,String charset) throws IOException {
		return read(new URL(url), charset, 50000);
	}
	
	public static String read(File file,String charset) throws IOException {
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			return read(input, charset);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (input!=null) {
				input.close();
				input=null;
			}
		}
	}
	
	public static Document parse(String s) {
		return DocumentParser.getDefaultDocumentParser().parseDocument(s);
	}
	
	public static Document parse(InputStream input,String charset) throws IOException {
		return parse(read(input, charset));
	}
	
	public static Document parse(File file,String charset) throws IOException {
		return parse(read(file, charset));
	}

	public static Document parse(URL url,String charset,int timeout) throws IOException {
		return parse(read(url, charset, timeout));
	}

	public static Document parse(URL url,String charset) throws IOException {
		return parse(url, charset, 50000);
	}
	
	public static Document parse(String url,String charset) throws IOException {
		return parse(new URL(url), charset);
	}
}
