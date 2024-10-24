/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutImpl extends DLFileShortcutBaseImpl {

	@Override
	public String buildTreePath() throws PortalException {
		if (getFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return StringPool.SLASH;
		}

		DLFolder dlFolder = getDLFolder();

		return dlFolder.buildTreePath();
	}

	@Override
	public DLFolder getDLFolder() throws PortalException {
		Folder folder = getFolder();

		return (DLFolder)folder.getModel();
	}

	@Override
	public FileVersion getFileVersion() throws PortalException {
		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			getToFileEntryId());

		return fileEntry.getFileVersion();
	}

	@Override
	public Folder getFolder() throws PortalException {
		if (getFolderId() <= 0) {
			return new LiferayFolder(new DLFolderImpl());
		}

		return DLAppLocalServiceUtil.getFolder(getFolderId());
	}

	@Override
	public String getToTitle() {
		String toTitle = null;

		try {
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				getToFileEntryId());

			toTitle = fileEntry.getTitle();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return toTitle;
	}

	@Override
	public boolean isInHiddenFolder() {
		try {
			long repositoryId = getRepositoryId();

			if (getGroupId() == repositoryId) {
				return false;
			}

			Repository repository = RepositoryLocalServiceUtil.getRepository(
				repositoryId);

			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
				repository.getDlFolderId());

			return dlFolder.isHidden();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		return false;
	}

	@Override
	public boolean isInTrash() {
		if (super.isInTrash() || !isActive()) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileShortcutImpl.class);

}