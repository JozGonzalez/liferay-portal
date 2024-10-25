/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.security.permission.resource;

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.permission.CommercePriceListPermission;
import com.liferay.commerce.price.list.service.CommercePriceListChannelRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "model.class.name=com.liferay.commerce.price.list.model.CommercePriceListChannelRel",
	service = ModelResourcePermission.class
)
public class CommercePriceListChannelRelModelResourcePermission
	implements ModelResourcePermission<CommercePriceListChannelRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommercePriceListChannelRel commercePriceListChannelRel,
			String actionId)
		throws PortalException {

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListChannelRel.getCommercePriceListId(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commercePriceListChannelRelId, String actionId)
		throws PortalException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelLocalService.
				getCommercePriceListChannelRel(commercePriceListChannelRelId);

		commercePriceListPermission.check(
			permissionChecker,
			commercePriceListChannelRel.getCommercePriceListId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommercePriceListChannelRel commercePriceListChannelRel,
			String actionId)
		throws PortalException {

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListChannelRel.getCommercePriceListId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commercePriceListChannelRelId, String actionId)
		throws PortalException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelLocalService.
				getCommercePriceListChannelRel(commercePriceListChannelRelId);

		return commercePriceListPermission.contains(
			permissionChecker,
			commercePriceListChannelRel.getCommercePriceListId(), actionId);
	}

	@Override
	public String getModelName() {
		return CommercePriceListChannelRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommercePriceListChannelRelLocalService
		commercePriceListChannelRelLocalService;

	@Reference
	protected CommercePriceListPermission commercePriceListPermission;

}