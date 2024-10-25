/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class LongWrapper
	extends PrimitiveWrapper implements Comparable<LongWrapper> {

	public static final Class<?> TYPE = Long.TYPE;

	public LongWrapper() {
		this(0L);
	}

	public LongWrapper(long value) {
		_value = value;
	}

	@Override
	public int compareTo(LongWrapper longWrapper) {
		if (longWrapper == null) {
			return 1;
		}

		if (getValue() > longWrapper.getValue()) {
			return 1;
		}
		else if (getValue() < longWrapper.getValue()) {
			return -1;
		}

		return 0;
	}

	public long decrement() {
		return --_value;
	}

	public long getValue() {
		return _value;
	}

	public long increment() {
		return ++_value;
	}

	public void setValue(long value) {
		_value = value;
	}

	private long _value;

}