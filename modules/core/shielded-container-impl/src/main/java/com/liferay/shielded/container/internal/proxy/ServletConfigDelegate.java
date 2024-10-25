/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.shielded.container.internal.proxy;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class ServletConfigDelegate {

	public ServletConfigDelegate(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	private final ServletContext _servletContext;

}