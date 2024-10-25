/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.onedrive.web.internal;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.File;

/**
 * Represents a link between a Documents and Media {@code FileEntry} and a
 * Office 365 file. The methods in {@link DLOpenerOneDriveManager} return
 * instances of this class, which you should never create directly.
 *
 * @author Cristina González Castellano
 * @review
 */
public class DLOpenerOneDriveFileReference<E extends PortalException> {

	/**
	 * Creates a new {@code DLOpenerOneDriveFileReference}.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  titleUnsafeSupplier the supplier that provides the document's
	 *         title when invoked
	 * @param  fileUnsafeSupplier the supplier that provides the document's
	 *         contents when invoked
	 * @review
	 */
	public DLOpenerOneDriveFileReference(
		long fileEntryId, UnsafeSupplier<String, E> titleUnsafeSupplier,
		UnsafeSupplier<File, E> fileUnsafeSupplier) {

		this(fileEntryId, titleUnsafeSupplier, fileUnsafeSupplier, 0);
	}

	/**
	 * Creates a new {@code DLOpenerOneDriveFileReference}.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  titleUnsafeSupplier the supplier that provides the document's
	 *         title when invoked
	 * @param  backgroundTaskId the background task ID
	 * @review
	 */
	public DLOpenerOneDriveFileReference(
		long fileEntryId, UnsafeSupplier<String, E> titleUnsafeSupplier,
		UnsafeSupplier<File, E> fileUnsafeSupplier, long backgroundTaskId) {

		_fileEntryId = fileEntryId;
		_titleUnsafeSupplier = titleUnsafeSupplier;
		_fileUnsafeSupplier = fileUnsafeSupplier;
		_backgroundTaskId = backgroundTaskId;
	}

	/**
	 * Returns the primary key of the background task that uploads the original
	 * file contents to OneDrive. If this method returns {@code 0}, no upload
	 * task is in progress.
	 *
	 * @return the primary key of the background task
	 * @review
	 */
	public long getBackgroundTaskId() {
		return _backgroundTaskId;
	}

	/**
	 * Returns a file with this OneDrive file reference's content.
	 *
	 * @return the file
	 * @review
	 */
	public File getContentFile() throws PortalException {
		return _fileUnsafeSupplier.get();
	}

	/**
	 * Returns the primary key of the file entry linked to this reference.
	 *
	 * @return the primary key of the file entry
	 * @review
	 */
	public long getFileEntryId() {
		return _fileEntryId;
	}

	/**
	 * Returns this OneDrive file reference's title.
	 *
	 * @return the title
	 * @review
	 */
	public String getTitle() throws PortalException {
		return _titleUnsafeSupplier.get();
	}

	private final long _backgroundTaskId;
	private final long _fileEntryId;
	private final UnsafeSupplier<File, E> _fileUnsafeSupplier;
	private final UnsafeSupplier<String, E> _titleUnsafeSupplier;

}