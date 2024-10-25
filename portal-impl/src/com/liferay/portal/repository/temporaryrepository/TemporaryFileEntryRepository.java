/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.temporaryrepository;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.RepositoryService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;

/**
 * @author Iván Zaera
 */
public class TemporaryFileEntryRepository extends LiferayRepository {

	public TemporaryFileEntryRepository(
		RepositoryLocalService repositoryLocalService,
		RepositoryService repositoryService,
		DLAppHelperLocalService dlAppHelperLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryService dlFileEntryService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		DLFileShortcutLocalService dlFileShortcutLocalService,
		DLFileShortcutService dlFileShortcutService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService,
		ResourceLocalService resourceLocalService, long groupId,
		long repositoryId, long dlFolderId) {

		super(
			repositoryLocalService, repositoryService, dlAppHelperLocalService,
			dlFileEntryLocalService, dlFileEntryService,
			dlFileEntryTypeLocalService, dlFileShortcutLocalService,
			dlFileShortcutService, dlFileVersionLocalService,
			dlFileVersionService, dlFolderLocalService, dlFolderService,
			resourceLocalService, groupId, repositoryId, dlFolderId);
	}

	@Override
	public void deleteAll() {
		_runWithoutSystemEvents(
			() -> {
				super.deleteAll();

				return null;
			});
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		_runWithoutSystemEvents(
			() -> {
				super.deleteFileEntry(fileEntryId);

				return null;
			});
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException {

		_runWithoutSystemEvents(
			() -> {
				super.deleteFileEntry(folderId, title);

				return null;
			});
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		_runWithoutSystemEvents(
			() -> {
				super.deleteFileShortcut(fileShortcutId);

				return null;
			});
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		_runWithoutSystemEvents(
			() -> {
				super.deleteFileShortcuts(toFileEntryId);

				return null;
			});
	}

	@Override
	public void deleteFileVersion(long fileVersionId) throws PortalException {
		_runWithoutSystemEvents(
			() -> {
				super.deleteFileVersion(fileVersionId);

				return null;
			});
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException {

		_runWithoutSystemEvents(
			() -> {
				super.deleteFileVersion(fileEntryId, version);

				return null;
			});
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		_runWithoutSystemEvents(
			() -> {
				super.deleteFolder(folderId);

				return null;
			});
	}

	@Override
	public void deleteFolder(long parentFolderId, String name)
		throws PortalException {

		_runWithoutSystemEvents(
			() -> {
				super.deleteFolder(parentFolderId, name);

				return null;
			});
	}

	private <T extends Throwable> void _runWithoutSystemEvents(
			UnsafeSupplier<Void, T> unsafeSupplier)
		throws T {

		SystemEventHierarchyEntryThreadLocal.push(DLFileEntry.class);

		try {
			unsafeSupplier.get();
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.pop(DLFileEntry.class);
		}
	}

}