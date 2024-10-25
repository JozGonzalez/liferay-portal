/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external.model;

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;
import com.liferay.petra.string.StringPool;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SharepointFileVersion
	implements ExtRepositoryFileVersion, SharepointModel {

	public SharepointFileVersion(
		String extRepositoryModelKey, String version, String contentURL,
		Date createDate, String changeLog, String mimeType, long size,
		ExtRepositoryFileEntry extRepositoryFileEntry) {

		_extRepositoryModelKey = extRepositoryModelKey;
		_version = version;
		_contentURL = contentURL;
		_createDate = createDate;
		_changeLog = changeLog;
		_mimeType = mimeType;
		_size = size;
		_extRepositoryFileEntry = extRepositoryFileEntry;
	}

	@Override
	public ExtRepositoryFileVersion asFileVersion() {
		return this;
	}

	@Override
	public String getCanonicalContentURL() {
		return _contentURL;
	}

	@Override
	public String getChangeLog() {
		return _changeLog;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public ExtRepositoryFileEntry getExtRepositoryFileEntry() {
		return _extRepositoryFileEntry;
	}

	@Override
	public String getExtRepositoryModelKey() {
		return _extRepositoryModelKey;
	}

	@Override
	public String getMimeType() {
		return _mimeType;
	}

	@Override
	public String getOwner() {
		return StringPool.BLANK;
	}

	@Override
	public long getSize() {
		return _size;
	}

	@Override
	public String getVersion() {
		return _version;
	}

	private final String _changeLog;
	private final String _contentURL;
	private final Date _createDate;
	private final ExtRepositoryFileEntry _extRepositoryFileEntry;
	private final String _extRepositoryModelKey;
	private final String _mimeType;
	private final long _size;
	private final String _version;

}