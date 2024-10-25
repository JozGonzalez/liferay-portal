/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.xml.xpath.LiferayFunctionContext;
import com.liferay.portal.xml.xpath.LiferayNamespaceContext;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import org.jaxen.FunctionContext;
import org.jaxen.NamespaceContext;

/**
 * @author Brian Wing Shun Chan
 */
public class XPathImpl implements XPath {

	public XPathImpl(
		org.dom4j.XPath xPath, Map<String, String> namespaceContextMap) {

		_xPath = xPath;

		_xPath.setFunctionContext(_functionContext);

		NamespaceContext namespaceContext = new LiferayNamespaceContext(
			namespaceContextMap);

		_xPath.setNamespaceContext(namespaceContext);
	}

	@Override
	public boolean booleanValueOf(Object context) {
		return _xPath.booleanValueOf(toOldContext(context));
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof XPathImpl)) {
			return false;
		}

		XPathImpl xPathImpl = (XPathImpl)object;

		org.dom4j.XPath xPath = xPathImpl.getWrappedXPath();

		return _xPath.equals(xPath);
	}

	@Override
	public Object evaluate(Object context) {
		return toNewContext(_xPath.evaluate(toOldContext(context)));
	}

	@Override
	public String getText() {
		return _xPath.getText();
	}

	public org.dom4j.XPath getWrappedXPath() {
		return _xPath;
	}

	@Override
	public int hashCode() {
		return _xPath.hashCode();
	}

	@Override
	public boolean matches(Node node) {
		NodeImpl nodeImpl = (NodeImpl)node;

		return _xPath.matches(nodeImpl.getWrappedNode());
	}

	@Override
	public Number numberValueOf(Object context) {
		return _xPath.numberValueOf(toOldContext(context));
	}

	@Override
	public List<Node> selectNodes(Object context) {
		return SAXReaderImpl.toNewNodes(
			_xPath.selectNodes(toOldContext(context)));
	}

	@Override
	public List<Node> selectNodes(Object context, XPath sortXPath) {
		XPathImpl sortXPathImpl = (XPathImpl)sortXPath;

		return SAXReaderImpl.toNewNodes(
			_xPath.selectNodes(
				toOldContext(context), sortXPathImpl.getWrappedXPath()));
	}

	@Override
	public List<Node> selectNodes(
		Object context, XPath sortXPath, boolean distinct) {

		XPathImpl sortXPathImpl = (XPathImpl)sortXPath;

		return SAXReaderImpl.toNewNodes(
			_xPath.selectNodes(
				toOldContext(context), sortXPathImpl.getWrappedXPath(),
				distinct));
	}

	@Override
	public Node selectSingleNode(Object context) {
		org.dom4j.Node node = _xPath.selectSingleNode(toOldContext(context));

		if (node == null) {
			return null;
		}
		else if (node instanceof Element) {
			return new ElementImpl((Element)node);
		}

		return new NodeImpl(node);
	}

	@Override
	public void sort(List<Node> nodes) {
		_xPath.sort(SAXReaderImpl.toOldNodes(nodes));
	}

	@Override
	public void sort(List<Node> nodes, boolean distinct) {
		_xPath.sort(SAXReaderImpl.toOldNodes(nodes), distinct);
	}

	@Override
	public String toString() {
		return _xPath.toString();
	}

	@Override
	public String valueOf(Object context) {
		return _xPath.valueOf(toOldContext(context));
	}

	protected Object toNewContext(Object context) {
		if (context == null) {
			return null;
		}
		else if (context instanceof Document) {
			Document document = (Document)context;

			return new DocumentImpl(document);
		}
		else if (context instanceof Element) {
			Element element = (Element)context;

			return new ElementImpl(element);
		}
		else if (context instanceof org.dom4j.Node) {
			org.dom4j.Node node = (org.dom4j.Node)context;

			return new NodeImpl(node);
		}
		else if (context instanceof List<?>) {
			return SAXReaderImpl.toNewNodes((List<org.dom4j.Node>)context);
		}

		return context;
	}

	protected Object toOldContext(Object context) {
		if (context == null) {
			return null;
		}
		else if (context instanceof DocumentImpl) {
			DocumentImpl documentImpl = (DocumentImpl)context;

			return documentImpl.getWrappedDocument();
		}
		else if (context instanceof NodeImpl) {
			NodeImpl nodeImpl = (NodeImpl)context;

			return nodeImpl.getWrappedNode();
		}
		else if (context instanceof List<?>) {
			return SAXReaderImpl.toOldNodes((List<Node>)context);
		}

		return context;
	}

	private static final FunctionContext _functionContext =
		new LiferayFunctionContext();

	private final org.dom4j.XPath _xPath;

}