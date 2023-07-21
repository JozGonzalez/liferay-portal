/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryAudit;
import com.liferay.commerce.inventory.service.base.CommerceInventoryAuditLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.inventory.model.CommerceInventoryAudit",
	service = AopService.class
)
public class CommerceInventoryAuditLocalServiceImpl
	extends CommerceInventoryAuditLocalServiceBaseImpl {

	@Override
	public CommerceInventoryAudit addCommerceInventoryAudit(
			long userId, String logType, String logTypeSettings, int quantity,
			String sku, String unitOfMeasureKey)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		long commerceInventoryAuditId = counterLocalService.increment();

		CommerceInventoryAudit commerceInventoryAudit =
			commerceInventoryAuditPersistence.create(commerceInventoryAuditId);

		commerceInventoryAudit.setCompanyId(user.getCompanyId());
		commerceInventoryAudit.setUserId(user.getUserId());
		commerceInventoryAudit.setUserName(user.getFullName());
		commerceInventoryAudit.setLogType(logType);
		commerceInventoryAudit.setLogTypeSettings(logTypeSettings);
		commerceInventoryAudit.setQuantity(quantity);
		commerceInventoryAudit.setUnitOfMeasureKey(unitOfMeasureKey);
		commerceInventoryAudit.setSku(sku);

		return commerceInventoryAuditPersistence.update(commerceInventoryAudit);
	}

	@Override
	public void checkCommerceInventoryAudit(Date date) {
		commerceInventoryAuditPersistence.removeByLtCreateDate(date);
	}

	@Override
	public List<CommerceInventoryAudit> getCommerceInventoryAudits(
		long companyId, String sku, int start, int end) {

		return commerceInventoryAuditPersistence.findByC_S(
			companyId, sku, start, end);
	}

	@Override
	public int getCommerceInventoryAuditsCount(long companyId, String sku) {
		return commerceInventoryAuditPersistence.countByC_S(companyId, sku);
	}

	@Reference
	private UserLocalService _userLocalService;

}