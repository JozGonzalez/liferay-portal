/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.util.Date;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public class SharepointObject {

	public SharepointObject(
		String author, String checkedOutBy, Date createdDate, boolean folder,
		Date lastModifiedDate, String path, Set<Permission> permissions,
		long sharepointObjectId, long size, URL url) {

		_author = author;
		_checkedOutBy = checkedOutBy;
		_createdDate = createdDate;
		_folder = folder;
		_lastModifiedDate = lastModifiedDate;
		_path = path;
		_permissions = permissions;
		_sharepointObjectId = sharepointObjectId;
		_size = size;
		_url = url;

		_extension = _getExtension(path);
		_folderPath = _getFolderPath(path);
		_name = _getName(path);
	}

	public String getAuthor() {
		return _author;
	}

	public String getCheckedOutBy() {
		return _checkedOutBy;
	}

	public Date getCreatedDate() {
		return _createdDate;
	}

	public String getExtension() {
		return _extension;
	}

	public String getFolderPath() {
		return _folderPath;
	}

	public Date getLastModifiedDate() {
		return _lastModifiedDate;
	}

	public String getName() {
		return _name;
	}

	public String getPath() {
		return _path;
	}

	public Set<Permission> getPermissions() {
		return _permissions;
	}

	public long getSharepointObjectId() {
		return _sharepointObjectId;
	}

	public long getSize() {
		return _size;
	}

	public URL getURL() {
		return _url;
	}

	public boolean isFile() {
		return !isFolder();
	}

	public boolean isFolder() {
		return _folder;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{_author=", _author, ", checkedOutBy=", _checkedOutBy,
			", createdDate=", _createdDate, ", extension=", _extension,
			", folderPath=", _folderPath, ", folder=", _folder,
			", lastModifiedDate=", _lastModifiedDate, ", name=", _name,
			", path=", _path, ", permissions=", _permissions,
			", sharepointObjectId=", _sharepointObjectId, ", size=", _size,
			", url=", _url, "}");
	}

	public enum Permission {

		ADD_AND_CUSTOMIZE_PAGES(0x0000000000040000L),
		ADD_DEL_PRIVATE_WEB_PARTS(0x0000000010000000L),
		ADD_LIST_ITEMS(0x0000000000000002L),
		APPLY_STYLE_SHEETS(0x0000000000100000L),
		APPLY_THEME_AND_BORDER(0x0000000000080000L),
		APPROVE_ITEMS(0x0000000000000010L),
		BROWSE_DIRECTORIES(0x0000000004000000L),
		BROWSE_USER_INFO(0x0000000008000000L),
		CANCEL_CHECKOUT(0x0000000000000100L),
		CREATE_ALERTS(0x0000008000000000L), CREATE_GROUPS(0x0000000001000000L),
		CREATE_SSC_SITE(0x0000000000400000L),
		DELETE_LIST_ITEMS(0x0000000000000008L),
		DELETE_VERSIONS(0x0000000000000080L),
		EDIT_LIST_ITEMS(0x0000000000000004L),
		EDIT_MY_USER_INFO(0x0000010000000000L),
		ENUMERATE_PERMISSIONS(0x4000000000000000L),
		MANAGE_ALERTS(0x0000004000000000L), MANAGE_LISTS(0x0000000000000800L),
		MANAGE_PERMISSIONS(0x0000000002000000L),
		MANAGE_PERSONAL_VIEWS(0x0000000000000200L),
		MANAGE_SUBWEBS(0x0000000000800000L), MANAGE_WEB(0x0000000040000000L),
		OPEN(0x0000000000010000L), OPEN_ITEMS(0x0000000000000020L),
		UPDATE_PERSONAL_WEB_PARTS(0x0000000020000000L),
		USE_CLIENT_INTEGRATION(0x0000001000000000L),
		USE_REMOTE_APIS(0x0000002000000000L),
		VIEW_FORM_PAGES(0x0000000000001000L),
		VIEW_LIST_ITEMS(0x0000000000000001L), VIEW_PAGES(0x0000000000020000L),
		VIEW_USAGE_DATA(0x0000000000200000L),
		VIEW_VERSIONS(0x0000000000000040L);

		public long getMask() {
			return _mask;
		}

		private Permission(long mask) {
			_mask = mask;
		}

		private final long _mask;

	}

	private String _getExtension(String path) {
		int index = path.lastIndexOf(StringPool.PERIOD);

		if (index == -1) {
			return StringPool.BLANK;
		}

		return path.substring(index + 1);
	}

	private String _getFolderPath(String path) {
		int index = path.lastIndexOf(StringPool.SLASH);

		if (index == 0) {
			return StringPool.SLASH;
		}

		return path.substring(0, index);
	}

	private String _getName(String path) {
		if (path.equals(StringPool.SLASH)) {
			return StringPool.SLASH;
		}

		int index = path.lastIndexOf(StringPool.SLASH);

		return path.substring(index + 1);
	}

	private final String _author;
	private final String _checkedOutBy;
	private final Date _createdDate;
	private final String _extension;
	private final boolean _folder;
	private final String _folderPath;
	private final Date _lastModifiedDate;
	private final String _name;
	private final String _path;
	private final Set<Permission> _permissions;
	private final long _sharepointObjectId;
	private final long _size;
	private final URL _url;

}