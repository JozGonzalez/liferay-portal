/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.json;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONObjectWrapper {

	public JSONObjectWrapper(Object value) {
		_value = value;
	}

	public Object getValue() {
		return _value;
	}

	private final Object _value;

}