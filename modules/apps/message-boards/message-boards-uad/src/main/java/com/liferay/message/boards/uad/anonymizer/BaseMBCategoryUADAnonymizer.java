/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.uad.anonymizer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.uad.constants.MBUADConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the message boards category UAD anonymizer.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom service methods should be put in
 * {@link MBCategoryUADAnonymizer}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseMBCategoryUADAnonymizer
	extends DynamicQueryUADAnonymizer<MBCategory> {

	@Override
	public void autoAnonymize(
			MBCategory mbCategory, long userId, User anonymousUser)
		throws PortalException {

		if (mbCategory.getUserId() == userId) {
			mbCategory.setUserId(anonymousUser.getUserId());
			mbCategory.setUserName(anonymousUser.getFullName());

			autoAnonymizeAssetEntry(mbCategory, anonymousUser);
		}

		if (mbCategory.getStatusByUserId() == userId) {
			mbCategory.setStatusByUserId(anonymousUser.getUserId());
			mbCategory.setStatusByUserName(anonymousUser.getFullName());
		}

		mbCategoryLocalService.updateMBCategory(mbCategory);
	}

	@Override
	public void delete(MBCategory mbCategory) throws PortalException {
		mbCategoryLocalService.deleteCategory(mbCategory);
	}

	@Override
	public Class<MBCategory> getTypeClass() {
		return MBCategory.class;
	}

	protected void autoAnonymizeAssetEntry(
		MBCategory mbCategory, User anonymousUser) {

		AssetEntry assetEntry = fetchAssetEntry(mbCategory);

		if (assetEntry != null) {
			assetEntry.setUserId(anonymousUser.getUserId());
			assetEntry.setUserName(anonymousUser.getFullName());

			assetEntryLocalService.updateAssetEntry(assetEntry);
		}
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return mbCategoryLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return MBUADConstants.USER_ID_FIELD_NAMES_MB_CATEGORY;
	}

	protected AssetEntry fetchAssetEntry(MBCategory mbCategory) {
		return assetEntryLocalService.fetchEntry(
			MBCategory.class.getName(), mbCategory.getCategoryId());
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected MBCategoryLocalService mbCategoryLocalService;

}