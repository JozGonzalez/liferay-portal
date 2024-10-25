/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class CMISModelCache {

	public List<FileEntry> getFileEntries(long folderId) {
		Map<Long, List<FileEntry>> fileEntriesMap = _fileEntriesMaps.get();

		return fileEntriesMap.get(folderId);
	}

	public FileEntry getFileEntry(long fileEntryId) {
		Map<Long, FileEntry> fileEntryMap = _fileEntryMaps.get();

		return fileEntryMap.get(fileEntryId);
	}

	public Folder getFolder(long folderId) {
		Map<Long, Folder> folderMap = _folderMaps.get();

		return folderMap.get(folderId);
	}

	public List<Folder> getFolders(long folderId) {
		Map<Long, List<Folder>> foldersMap = _foldersMap.get();

		return foldersMap.get(folderId);
	}

	public List<Object> getFoldersAndFileEntries(long folderId) {
		Map<Long, List<Object>> foldersAndFileEntriesMap =
			_foldersAndFileEntriesMaps.get();

		return foldersAndFileEntriesMap.get(folderId);
	}

	public void putFileEntries(long folderId, List<FileEntry> fileEntries) {
		Map<Long, List<FileEntry>> fileEntriesMap = _fileEntriesMaps.get();

		fileEntriesMap.put(folderId, fileEntries);
	}

	public void putFileEntry(FileEntry fileEntry) {
		if (fileEntry == null) {
			return;
		}

		Map<Long, FileEntry> fileEntryMap = _fileEntryMaps.get();

		fileEntryMap.put(fileEntry.getFileEntryId(), fileEntry);
	}

	public void putFolder(Folder folder) {
		if (folder == null) {
			return;
		}

		Map<Long, Folder> folderMap = _folderMaps.get();

		folderMap.put(folder.getFolderId(), folder);
	}

	public void putFolders(long folderId, List<Folder> folders) {
		Map<Long, List<Folder>> foldersMap = _foldersMap.get();

		foldersMap.put(folderId, folders);
	}

	public void putFoldersAndFileEntries(
		long folderId, List<Object> foldersAndFileEntries) {

		Map<Long, List<Object>> foldersAndFileEntriesMap =
			_foldersAndFileEntriesMaps.get();

		foldersAndFileEntriesMap.put(folderId, foldersAndFileEntries);
	}

	private final ThreadLocal<Map<Long, List<FileEntry>>> _fileEntriesMaps =
		new CentralizedThreadLocal<>(
			CMISRepository.class + "._fileEntriesMaps", HashMap::new);
	private final ThreadLocal<Map<Long, FileEntry>> _fileEntryMaps =
		new CentralizedThreadLocal<>(
			CMISRepository.class + "._fileEntryMaps", HashMap::new);
	private final ThreadLocal<Map<Long, Folder>> _folderMaps =
		new CentralizedThreadLocal<>(
			CMISRepository.class + "._folderMaps", HashMap::new);
	private final ThreadLocal<Map<Long, List<Object>>>
		_foldersAndFileEntriesMaps = new CentralizedThreadLocal<>(
			CMISRepository.class + "._foldersAndFileEntriesMaps", HashMap::new);
	private final ThreadLocal<Map<Long, List<Folder>>> _foldersMap =
		new CentralizedThreadLocal<>(
			CMISRepository.class + "._foldersMap", HashMap::new);

}