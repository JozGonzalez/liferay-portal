/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.model.impl;

import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalServiceUtil;

/**
 * The extended model base implementation for the AssetAutoTaggerEntry service. Represents a row in the &quot;AssetAutoTaggerEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AssetAutoTaggerEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntryImpl
 * @see AssetAutoTaggerEntry
 * @generated
 */
public abstract class AssetAutoTaggerEntryBaseImpl
	extends AssetAutoTaggerEntryModelImpl implements AssetAutoTaggerEntry {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset auto tagger entry model instance should use the <code>AssetAutoTaggerEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			AssetAutoTaggerEntryLocalServiceUtil.addAssetAutoTaggerEntry(this);
		}
		else {
			AssetAutoTaggerEntryLocalServiceUtil.updateAssetAutoTaggerEntry(
				this);
		}
	}

}