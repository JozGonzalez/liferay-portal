/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.uad.anonymizer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.uad.constants.KaleoUADConstants;
import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the kaleo task assignment instance UAD anonymizer.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom service methods should be put in
 * {@link KaleoTaskAssignmentInstanceUADAnonymizer}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseKaleoTaskAssignmentInstanceUADAnonymizer
	extends DynamicQueryUADAnonymizer<KaleoTaskAssignmentInstance> {

	@Override
	public void autoAnonymize(
			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
			long userId, User anonymousUser)
		throws PortalException {

		if (kaleoTaskAssignmentInstance.getUserId() == userId) {
			kaleoTaskAssignmentInstance.setUserId(anonymousUser.getUserId());
			kaleoTaskAssignmentInstance.setUserName(
				anonymousUser.getFullName());

			autoAnonymizeAssetEntry(kaleoTaskAssignmentInstance, anonymousUser);
		}

		kaleoTaskAssignmentInstanceLocalService.
			updateKaleoTaskAssignmentInstance(kaleoTaskAssignmentInstance);
	}

	@Override
	public void delete(KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance)
		throws PortalException {

		kaleoTaskAssignmentInstanceLocalService.
			deleteKaleoTaskAssignmentInstance(kaleoTaskAssignmentInstance);
	}

	@Override
	public Class<KaleoTaskAssignmentInstance> getTypeClass() {
		return KaleoTaskAssignmentInstance.class;
	}

	protected void autoAnonymizeAssetEntry(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
		User anonymousUser) {

		AssetEntry assetEntry = fetchAssetEntry(kaleoTaskAssignmentInstance);

		if (assetEntry != null) {
			assetEntry.setUserId(anonymousUser.getUserId());
			assetEntry.setUserName(anonymousUser.getFullName());

			assetEntryLocalService.updateAssetEntry(assetEntry);
		}
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return kaleoTaskAssignmentInstanceLocalService.
			getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return KaleoUADConstants.
			USER_ID_FIELD_NAMES_KALEO_TASK_ASSIGNMENT_INSTANCE;
	}

	protected AssetEntry fetchAssetEntry(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		return assetEntryLocalService.fetchEntry(
			KaleoTaskAssignmentInstance.class.getName(),
			kaleoTaskAssignmentInstance.getKaleoTaskAssignmentInstanceId());
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected KaleoTaskAssignmentInstanceLocalService
		kaleoTaskAssignmentInstanceLocalService;

}