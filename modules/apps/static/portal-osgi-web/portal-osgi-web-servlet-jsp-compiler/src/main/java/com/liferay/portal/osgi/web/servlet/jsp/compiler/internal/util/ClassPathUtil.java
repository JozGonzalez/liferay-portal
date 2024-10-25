/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.URLCodec;

import java.io.File;
import java.io.IOException;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.Objects;

/**
 * @author Miguel Pastor
 */
public class ClassPathUtil {

	public static File getFile(URL url) throws IOException {
		String fileName = null;

		URLConnection urlConnection = url.openConnection();

		if (urlConnection instanceof JarURLConnection) {
			JarURLConnection jarURLConnection = (JarURLConnection)urlConnection;

			URL jarFileURL = jarURLConnection.getJarFileURL();

			fileName = jarFileURL.getFile();
		}
		else if (Objects.equals(url.getProtocol(), "vfs")) {

			// JBoss uses a custom vfs protocol to represent JAR files

			fileName = url.getFile();

			int index = fileName.indexOf(".jar");

			if (index > 0) {
				fileName = fileName.substring(0, index + 4);
			}
		}
		else if (Objects.equals(url.getProtocol(), "wsjar")) {

			// WebSphere uses a custom wsjar protocol to represent JAR files

			fileName = url.getFile();

			String protocol = "file:";

			int index = fileName.indexOf(protocol);

			if (index > -1) {
				fileName = fileName.substring(protocol.length());
			}

			index = fileName.indexOf('!');

			if (index > -1) {
				fileName = fileName.substring(0, index);
			}
		}
		else if (Objects.equals(url.getProtocol(), "zip")) {

			// Weblogic uses a custom zip protocol to represent JAR files

			fileName = url.getFile();

			int index = fileName.indexOf('!');

			if (index > 0) {
				fileName = fileName.substring(0, index);
			}
		}
		else if (Objects.equals(url.getProtocol(), "reference") ||
				 Objects.equals(url.getProtocol(), "file")) {

			fileName = url.getFile();
		}
		else {

			// Ignore files that we do not know how to handle

			return null;
		}

		return new File(URLCodec.decodeURL(fileName, StringPool.UTF8));
	}

}