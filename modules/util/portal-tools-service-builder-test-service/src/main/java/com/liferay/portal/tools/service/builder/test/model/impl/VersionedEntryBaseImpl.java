/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntry;
import com.liferay.portal.tools.service.builder.test.service.VersionedEntryLocalServiceUtil;

/**
 * The extended model base implementation for the VersionedEntry service. Represents a row in the &quot;VersionedEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link VersionedEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryImpl
 * @see VersionedEntry
 * @generated
 */
public abstract class VersionedEntryBaseImpl
	extends VersionedEntryModelImpl implements VersionedEntry {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a versioned entry model instance should use the <code>VersionedEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			VersionedEntryLocalServiceUtil.addVersionedEntry(this);
		}
		else {
			try {
				VersionedEntryLocalServiceUtil.updateVersionedEntry(this);
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}
	}

}