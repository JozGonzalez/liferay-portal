/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.builder.constants;

import com.liferay.portal.kernel.util.Portal;

/**
 * @author Sergio Jiménez del Coso
 */
public class HeadlessBuilderConstants {

	public static final String BASE_URL =
		Portal.PATH_MODULE + HeadlessBuilderConstants.BASE_URL_SUFFIX;

	public static final String BASE_URL_SUFFIX = "/c/";

}