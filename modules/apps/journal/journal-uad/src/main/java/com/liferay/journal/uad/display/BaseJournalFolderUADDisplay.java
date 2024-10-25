/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.uad.display;

import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.uad.constants.JournalUADConstants;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.display.BaseModelUADDisplay;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the JournalFolder UAD display.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom methods should be put in
 * {@link JournalFolderUADDisplay}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseJournalFolderUADDisplay
	extends BaseModelUADDisplay<JournalFolder> {

	@Override
	public JournalFolder get(Serializable primaryKey) throws PortalException {
		return journalFolderLocalService.getJournalFolder(
			Long.valueOf(primaryKey.toString()));
	}

	@Override
	public String[] getDisplayFieldNames() {
		return new String[] {"name", "description"};
	}

	@Override
	public Class<JournalFolder> getTypeClass() {
		return JournalFolder.class;
	}

	@Override
	protected long doCount(DynamicQuery dynamicQuery) {
		return journalFolderLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	protected DynamicQuery doGetDynamicQuery() {
		return journalFolderLocalService.dynamicQuery();
	}

	@Override
	protected List<JournalFolder> doGetRange(
		DynamicQuery dynamicQuery, int start, int end) {

		return journalFolderLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return JournalUADConstants.USER_ID_FIELD_NAMES_JOURNAL_FOLDER;
	}

	@Reference
	protected JournalFolderLocalService journalFolderLocalService;

}