/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.portal.tools.service.builder.test.model.CacheMissEntry;
import com.liferay.portal.tools.service.builder.test.service.CacheMissEntryLocalServiceUtil;

/**
 * The extended model base implementation for the CacheMissEntry service. Represents a row in the &quot;CacheMissEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CacheMissEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheMissEntryImpl
 * @see CacheMissEntry
 * @generated
 */
public abstract class CacheMissEntryBaseImpl
	extends CacheMissEntryModelImpl implements CacheMissEntry {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cache miss entry model instance should use the <code>CacheMissEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CacheMissEntryLocalServiceUtil.addCacheMissEntry(this);
		}
		else {
			CacheMissEntryLocalServiceUtil.updateCacheMissEntry(this);
		}
	}

}