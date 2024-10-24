/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.uad.display;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.display.BaseModelUADDisplay;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the BookmarksEntry UAD display.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom methods should be put in
 * {@link BookmarksEntryUADDisplay}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseBookmarksEntryUADDisplay
	extends BaseModelUADDisplay<BookmarksEntry> {

	@Override
	public BookmarksEntry get(Serializable primaryKey) throws PortalException {
		return bookmarksEntryLocalService.getBookmarksEntry(
			Long.valueOf(primaryKey.toString()));
	}

	@Override
	public String[] getDisplayFieldNames() {
		return new String[] {"name", "url", "description"};
	}

	@Override
	public Class<BookmarksEntry> getTypeClass() {
		return BookmarksEntry.class;
	}

	@Override
	protected long doCount(DynamicQuery dynamicQuery) {
		return bookmarksEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	protected DynamicQuery doGetDynamicQuery() {
		return bookmarksEntryLocalService.dynamicQuery();
	}

	@Override
	protected List<BookmarksEntry> doGetRange(
		DynamicQuery dynamicQuery, int start, int end) {

		return bookmarksEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return BookmarksUADConstants.USER_ID_FIELD_NAMES_BOOKMARKS_ENTRY;
	}

	@Reference
	protected BookmarksEntryLocalService bookmarksEntryLocalService;

}