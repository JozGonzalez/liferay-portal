/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xml;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface SAXReader {

	public Attribute createAttribute(
		Element element, QName qName, String value);

	public Attribute createAttribute(
		Element element, String name, String value);

	public Document createDocument();

	public Document createDocument(Element rootElement);

	public Document createDocument(String encoding);

	public Element createElement(QName qName);

	public Element createElement(String name);

	public Entity createEntity(String name, String text);

	public Namespace createNamespace(String uri);

	public Namespace createNamespace(String prefix, String uri);

	public ProcessingInstruction createProcessingInstruction(
		String target, Map<String, String> data);

	public ProcessingInstruction createProcessingInstruction(
		String target, String data);

	public QName createQName(String localName);

	public QName createQName(String localName, Namespace namespace);

	public Text createText(String text);

	public XPath createXPath(String xPathExpression);

	public XPath createXPath(
		String xPathExpression, Map<String, String> namespaceContextMap);

	public XPath createXPath(
		String xPathExpression, String prefix, String namespace);

	public Document read(File file) throws DocumentException;

	public Document read(File file, boolean validate) throws DocumentException;

	public Document read(InputStream inputStream) throws DocumentException;

	public Document read(InputStream inputStream, boolean validate)
		throws DocumentException;

	public Document read(Reader reader) throws DocumentException;

	public Document read(Reader reader, boolean validate)
		throws DocumentException;

	public Document read(String xml) throws DocumentException;

	public Document read(String xml, boolean validate) throws DocumentException;

	public Document read(String xml, XMLSchema xmlSchema)
		throws DocumentException;

	public Document read(URL url) throws DocumentException;

	public Document read(URL url, boolean validate) throws DocumentException;

	public Document readURL(String url)
		throws DocumentException, MalformedURLException;

	public Document readURL(String url, boolean validate)
		throws DocumentException, MalformedURLException;

	public List<Node> selectNodes(
		String xPathFilterExpression, List<Node> nodes);

	public List<Node> selectNodes(String xPathFilterExpression, Node node);

	public void sort(List<Node> nodes, String xPathExpression);

	public void sort(
		List<Node> nodes, String xPathExpression, boolean distinct);

}