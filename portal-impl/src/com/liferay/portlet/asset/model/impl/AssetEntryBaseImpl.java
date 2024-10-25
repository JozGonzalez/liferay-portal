/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;

/**
 * The extended model base implementation for the AssetEntry service. Represents a row in the &quot;AssetEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AssetEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryImpl
 * @see AssetEntry
 * @generated
 */
public abstract class AssetEntryBaseImpl
	extends AssetEntryModelImpl implements AssetEntry {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset entry model instance should use the <code>AssetEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			AssetEntryLocalServiceUtil.addAssetEntry(this);
		}
		else {
			AssetEntryLocalServiceUtil.updateAssetEntry(this);
		}
	}

}