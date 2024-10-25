/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class JournalFolderTable {

	public static final String TABLE_NAME = "JournalFolder";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"folderId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"parentFolderId", Types.BIGINT},
		{"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"restrictionType", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("parentFolderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("restrictionType", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table JournalFolder (uuid_ VARCHAR(75) null,folderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentFolderId LONG,treePath STRING null,name VARCHAR(100) null,description STRING null,restrictionType INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table JournalFolder";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C36B0443 on JournalFolder (companyId, status)",
		"create index IX_E988689E on JournalFolder (groupId, name[$COLUMN_LENGTH:100$])",
		"create unique index IX_65026705 on JournalFolder (groupId, parentFolderId, name[$COLUMN_LENGTH:100$])",
		"create index IX_EFD9CAC on JournalFolder (groupId, parentFolderId, status)",
		"create index IX_54F89E1F on JournalFolder (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_E002061 on JournalFolder (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}