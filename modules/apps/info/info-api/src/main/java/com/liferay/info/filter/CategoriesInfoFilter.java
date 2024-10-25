/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.filter;

/**
 * @author Eudaldo Alonso
 */
public class CategoriesInfoFilter implements InfoFilter {

	public static final String FILTER_TYPE_NAME = "category";

	public long[][] getCategoryIds() {
		return _categoryIds;
	}

	@Override
	public String getFilterTypeName() {
		return FILTER_TYPE_NAME;
	}

	public void setCategoryIds(long[][] categoryIds) {
		_categoryIds = categoryIds;
	}

	private long[][] _categoryIds;

}