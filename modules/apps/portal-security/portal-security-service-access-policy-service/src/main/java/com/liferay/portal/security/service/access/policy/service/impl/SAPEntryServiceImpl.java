/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.service.access.policy.constants.SAPActionKeys;
import com.liferay.portal.security.service.access.policy.constants.SAPConstants;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.base.SAPEntryServiceBaseImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=sap",
		"json.web.service.context.path=SAPEntry"
	},
	service = AopService.class
)
public class SAPEntryServiceImpl extends SAPEntryServiceBaseImpl {

	@Override
	public SAPEntry addSAPEntry(
			String allowedServiceSignatures, boolean defaultSAPEntry,
			boolean enabled, String name, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, SAPActionKeys.ACTION_ADD_SAP_ENTRY);

		return sapEntryLocalService.addSAPEntry(
			getUserId(), allowedServiceSignatures, defaultSAPEntry, enabled,
			name, titleMap, serviceContext);
	}

	@Override
	public SAPEntry deleteSAPEntry(long sapEntryId) throws PortalException {
		_sapEntryFolderModelResourcePermission.check(
			getPermissionChecker(), sapEntryId, ActionKeys.DELETE);

		return sapEntryLocalService.deleteSAPEntry(sapEntryId);
	}

	@Override
	public SAPEntry deleteSAPEntry(SAPEntry sapEntry) throws PortalException {
		_sapEntryFolderModelResourcePermission.check(
			getPermissionChecker(), sapEntry, ActionKeys.DELETE);

		return sapEntryLocalService.deleteSAPEntry(sapEntry);
	}

	@Override
	public SAPEntry fetchSAPEntry(long companyId, String name)
		throws PortalException {

		SAPEntry sapEntry = sapEntryPersistence.fetchByC_N(companyId, name);

		if (sapEntry != null) {
			_sapEntryFolderModelResourcePermission.check(
				getPermissionChecker(), sapEntry, ActionKeys.VIEW);
		}

		return sapEntry;
	}

	@Override
	public List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end) {

		return sapEntryPersistence.filterFindByCompanyId(companyId, start, end);
	}

	@Override
	public List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return sapEntryPersistence.filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanySAPEntriesCount(long companyId) {
		return sapEntryPersistence.filterCountByCompanyId(companyId);
	}

	@Override
	public SAPEntry getSAPEntry(long sapEntryId) throws PortalException {
		_sapEntryFolderModelResourcePermission.check(
			getPermissionChecker(), sapEntryId, ActionKeys.VIEW);

		return sapEntryPersistence.findByPrimaryKey(sapEntryId);
	}

	@Override
	public SAPEntry getSAPEntry(long companyId, String name)
		throws PortalException {

		SAPEntry sapEntry = sapEntryPersistence.findByC_N(companyId, name);

		_sapEntryFolderModelResourcePermission.check(
			getPermissionChecker(), sapEntry, ActionKeys.VIEW);

		return sapEntry;
	}

	@Override
	public SAPEntry updateSAPEntry(
			long sapEntryId, String allowedServiceSignatures,
			boolean defaultSAPEntry, boolean enabled, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		_sapEntryFolderModelResourcePermission.check(
			getPermissionChecker(), sapEntryId, ActionKeys.UPDATE);

		return sapEntryLocalService.updateSAPEntry(
			sapEntryId, allowedServiceSignatures, defaultSAPEntry, enabled,
			name, titleMap, serviceContext);
	}

	@Reference(target = "(resource.name=" + SAPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.portal.security.service.access.policy.model.SAPEntry)"
	)
	private ModelResourcePermission<SAPEntry>
		_sapEntryFolderModelResourcePermission;

}