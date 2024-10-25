/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Reuther
 */
public class LayoutPriorityComparator extends OrderByComparator<Layout> {

	public static final String ORDER_BY_ASC = "Layout.priority ASC";

	public static final String ORDER_BY_DESC = "Layout.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public LayoutPriorityComparator() {
		this(true);
	}

	public LayoutPriorityComparator(boolean ascending) {
		_ascending = ascending;

		_layout = null;
		_lessThan = false;
	}

	public LayoutPriorityComparator(Layout layout, boolean lessThan) {
		_layout = layout;
		_lessThan = lessThan;

		_ascending = true;
	}

	@Override
	public int compare(Layout layout1, Layout layout2) {
		int value = 0;

		int priority1 = -1;

		if (layout1 != null) {
			priority1 = layout1.getPriority();
		}

		int priority2 = -1;

		if (layout2 != null) {
			priority2 = layout2.getPriority();
		}

		if (priority1 > priority2) {
			value = 1;
		}
		else if (priority1 < priority2) {
			value = -1;
		}
		else {
			if (_layout != null) {
				if (_layout.equals(layout1)) {
					if (_lessThan) {
						value = 1;
					}
					else {
						value = -1;
					}
				}
				else if (_layout.equals(layout2)) {
					if (_lessThan) {
						value = -1;
					}
					else {
						value = 1;
					}
				}
			}
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;
	private final Layout _layout;
	private final boolean _lessThan;

}