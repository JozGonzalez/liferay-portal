/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;
import com.liferay.commerce.discount.service.CommerceDiscountUsageEntryLocalServiceUtil;

/**
 * The extended model base implementation for the CommerceDiscountUsageEntry service. Represents a row in the &quot;CommerceDiscountUsageEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceDiscountUsageEntryImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntryImpl
 * @see CommerceDiscountUsageEntry
 * @generated
 */
public abstract class CommerceDiscountUsageEntryBaseImpl
	extends CommerceDiscountUsageEntryModelImpl
	implements CommerceDiscountUsageEntry {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce discount usage entry model instance should use the <code>CommerceDiscountUsageEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CommerceDiscountUsageEntryLocalServiceUtil.
				addCommerceDiscountUsageEntry(this);
		}
		else {
			CommerceDiscountUsageEntryLocalServiceUtil.
				updateCommerceDiscountUsageEntry(this);
		}
	}

}