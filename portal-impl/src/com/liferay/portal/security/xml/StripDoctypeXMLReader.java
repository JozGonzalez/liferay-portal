/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.xml;

import java.io.FilterInputStream;
import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.Objects;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 * @author Tomas Polesovsky
 */
public class StripDoctypeXMLReader implements XMLReader {

	public StripDoctypeXMLReader(XMLReader xmlReader) {
		_xmlReader = xmlReader;
	}

	@Override
	public ContentHandler getContentHandler() {
		return _xmlReader.getContentHandler();
	}

	@Override
	public DTDHandler getDTDHandler() {
		return _xmlReader.getDTDHandler();
	}

	@Override
	public EntityResolver getEntityResolver() {
		return _xmlReader.getEntityResolver();
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return _xmlReader.getErrorHandler();
	}

	@Override
	public boolean getFeature(String name)
		throws SAXNotRecognizedException, SAXNotSupportedException {

		return _xmlReader.getFeature(name);
	}

	@Override
	public Object getProperty(String name)
		throws SAXNotRecognizedException, SAXNotSupportedException {

		return _xmlReader.getProperty(name);
	}

	public XMLReader getXmlReader() {
		return _xmlReader;
	}

	@Override
	public void parse(InputSource inputSource)
		throws IOException, SAXException {

		if (_disallowDoctypeDecl) {
			InputStream inputStream = inputSource.getByteStream();

			if (inputStream != null) {
				final StripDoctypeFilter stripDoctypeFilter =
					new StripDoctypeFilter(inputStream);

				inputSource.setByteStream(
					new FilterInputStream(inputStream) {

						@Override
						public int read() throws IOException {
							return stripDoctypeFilter.read();
						}

						@Override
						public int read(byte[] bytes, int offset, int length)
							throws IOException {

							return stripDoctypeFilter.read(
								bytes, offset, length);
						}

					});
			}

			Reader reader = inputSource.getCharacterStream();

			if (reader != null) {
				final StripDoctypeFilter stripDoctypeFilter =
					new StripDoctypeFilter(reader);

				inputSource.setCharacterStream(
					new FilterReader(reader) {

						@Override
						public int read() throws IOException {
							return stripDoctypeFilter.read();
						}

						@Override
						public int read(char[] chars, int offset, int length)
							throws IOException {

							return stripDoctypeFilter.read(
								chars, offset, length);
						}

					});
			}
		}

		_xmlReader.parse(inputSource);
	}

	@Override
	public void parse(String systemId) throws IOException, SAXException {
		_xmlReader.parse(systemId);
	}

	@Override
	public void setContentHandler(ContentHandler contentHandler) {
		_xmlReader.setContentHandler(contentHandler);
	}

	@Override
	public void setDTDHandler(DTDHandler dtdHandler) {
		_xmlReader.setDTDHandler(dtdHandler);
	}

	@Override
	public void setEntityResolver(EntityResolver entityResolver) {
		_xmlReader.setEntityResolver(entityResolver);
	}

	@Override
	public void setErrorHandler(ErrorHandler errorHandler) {
		_xmlReader.setErrorHandler(errorHandler);
	}

	@Override
	public void setFeature(String name, boolean value)
		throws SAXNotRecognizedException, SAXNotSupportedException {

		if (Objects.equals(
				name, "http://apache.org/xml/features/disallow-doctype-decl")) {

			_disallowDoctypeDecl = value;
		}

		_xmlReader.setFeature(name, value);
	}

	@Override
	public void setProperty(String name, Object value)
		throws SAXNotRecognizedException, SAXNotSupportedException {

		if (Objects.equals(
				name, "http://xml.org/sax/handlers/LexicalHandler")) {

			return;
		}

		_xmlReader.setProperty(name, value);
	}

	private boolean _disallowDoctypeDecl;
	private final XMLReader _xmlReader;

}