/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.uad.display;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.EntryLocalService;
import com.liferay.contacts.uad.constants.ContactsUADConstants;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.display.BaseModelUADDisplay;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the Entry UAD display.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom methods should be put in
 * {@link EntryUADDisplay}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseEntryUADDisplay extends BaseModelUADDisplay<Entry> {

	@Override
	public Entry get(Serializable primaryKey) throws PortalException {
		return entryLocalService.getEntry(Long.valueOf(primaryKey.toString()));
	}

	@Override
	public String[] getDisplayFieldNames() {
		return new String[] {"fullName", "emailAddress", "comments"};
	}

	@Override
	public Class<Entry> getTypeClass() {
		return Entry.class;
	}

	@Override
	protected long doCount(DynamicQuery dynamicQuery) {
		return entryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	protected DynamicQuery doGetDynamicQuery() {
		return entryLocalService.dynamicQuery();
	}

	@Override
	protected List<Entry> doGetRange(
		DynamicQuery dynamicQuery, int start, int end) {

		return entryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return ContactsUADConstants.USER_ID_FIELD_NAMES_ENTRY;
	}

	@Reference
	protected EntryLocalService entryLocalService;

}