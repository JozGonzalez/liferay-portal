/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.uad.anonymizer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.uad.constants.PortalUADConstants;
import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the repository UAD anonymizer.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom service methods should be put in
 * {@link RepositoryUADAnonymizer}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseRepositoryUADAnonymizer
	extends DynamicQueryUADAnonymizer<Repository> {

	@Override
	public void autoAnonymize(
			Repository repository, long userId, User anonymousUser)
		throws PortalException {

		if (repository.getUserId() == userId) {
			repository.setUserId(anonymousUser.getUserId());
			repository.setUserName(anonymousUser.getFullName());

			autoAnonymizeAssetEntry(repository, anonymousUser);
		}

		repositoryLocalService.updateRepository(repository);
	}

	@Override
	public void delete(Repository repository) throws PortalException {
		repositoryLocalService.deleteRepository(repository);
	}

	@Override
	public Class<Repository> getTypeClass() {
		return Repository.class;
	}

	protected void autoAnonymizeAssetEntry(
		Repository repository, User anonymousUser) {

		AssetEntry assetEntry = fetchAssetEntry(repository);

		if (assetEntry != null) {
			assetEntry.setUserId(anonymousUser.getUserId());
			assetEntry.setUserName(anonymousUser.getFullName());

			assetEntryLocalService.updateAssetEntry(assetEntry);
		}
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return repositoryLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return PortalUADConstants.USER_ID_FIELD_NAMES_REPOSITORY;
	}

	protected AssetEntry fetchAssetEntry(Repository repository) {
		return assetEntryLocalService.fetchEntry(
			Repository.class.getName(), repository.getRepositoryId());
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected RepositoryLocalService repositoryLocalService;

}