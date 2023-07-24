/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.constants.HeadlessBuilderConstants;

/**
 * @author Luis Miguel Barcos
 */
public class PathUtil {

	public static String sanitize(String path) {
		if (path.startsWith(HeadlessBuilderConstants.BASE_URL)) {
			path = path.substring(HeadlessBuilderConstants.BASE_URL.length());
		}

		return path;
	}

}