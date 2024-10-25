/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.revert.schema.version.model.impl;

import com.liferay.revert.schema.version.model.RSVEntry;
import com.liferay.revert.schema.version.service.RSVEntryLocalServiceUtil;

/**
 * The extended model base implementation for the RSVEntry service. Represents a row in the &quot;RSVEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link RSVEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RSVEntryImpl
 * @see RSVEntry
 * @generated
 */
public abstract class RSVEntryBaseImpl
	extends RSVEntryModelImpl implements RSVEntry {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a rsv entry model instance should use the <code>RSVEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			RSVEntryLocalServiceUtil.addRSVEntry(this);
		}
		else {
			RSVEntryLocalServiceUtil.updateRSVEntry(this);
		}
	}

}